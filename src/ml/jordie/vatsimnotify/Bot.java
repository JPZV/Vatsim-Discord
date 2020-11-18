package ml.jordie.vatsimnotify;

import ml.jordie.vatsimnotify.storage.NeoConfigFile;
import ml.jordie.vatsimnotify.vatsim.model.NotifyConfig;
import ml.jordie.vatsimnotify.vatsim.model.GlobalConfig;

import ml.jordie.vatsimnotify.vatsim.ControllerManager;
import ml.jordie.vatsimnotify.vatsim.Parser;
import ml.jordie.vatsimnotify.vatsim.model.Controller;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.awt.Color;
import java.util.*;

public class Bot {

    private JDA api;
    public static Bot instance;
    // This is used to calculate time between start, and current (preventing spam from "newController" Alerts).
    public long startTime;

    /**
     * @description Application entry method.
     * @param args
     */
    public static void main(String[] args) {
        new Bot().runBot();
    }

    public static Bot getInstance() {
        return instance;
    }

    /**
     * @description "run" method for the Bot. Should not be called more than once, as this is the method that
     *              logs the Bot into Discord.
     */
    private void runBot() {
        try {
            instance = this;

            api = JDABuilder.createDefault(NeoConfigFile.getConfig().getDiscordToken()).setActivity(Activity.watching("for new controllers!")).build();

            api.setAutoReconnect(true);

            startTime = System.currentTimeMillis();

            new ControllerManager();

            refreshData();
        }catch(LoginException e){
            System.err.println("Invalid Discord login token! Please double check your token.");
            System.exit(-1);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @description TimerTask used to call the Parser.parse method every 2 minutes (when Vatsim's Data Server updates).
     *              eventually I will improve this.
     */
    public void refreshData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    System.out.println("Running Sync!");
                    new Parser().parse();
                    ControllerManager.getInstance().purgeControllers();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 0, 1000 * 60 * 2);
    }

    /**
     * @description Method used to send login notifications. This will also filter out callsigns if that is enabled.
     * @param controller Controller that has gone online/offline.
     * @param offlineNotification boolean used to indicate wether this is an online controller, or offline controller notification (t = online|f = offline).
     */
    public void newControllerAlert(Controller controller, boolean offlineNotification) {
        if (System.currentTimeMillis() - startTime > 10000)
        {
        	GlobalConfig globalConfig = NeoConfigFile.getConfig(); 
        	
        	if (globalConfig.getZones() == null)
        		return;
        	for (NotifyConfig config : globalConfig.getZones())
        	{
        		if (config.getNotifyAll())
            	{
            		if(offlineNotification)
                        sendLogoutNotification(controller, config);
                    else
                        sendNotification(controller, config);
            		continue;
            	}
        		
        		if (config.getPositions() != null)
        		{
            		Boolean found = false;
	        		for (String pos : config.getPositions())
	        		{
	                    if (controller.getCallsign().startsWith(pos))
	                    {
	                        if(offlineNotification)
	                            sendLogoutNotification(controller, config);
	                        else
	                            sendNotification(controller, config);
	                        
	                        found = true;
	                        break;
	                    }
	        		}
	        		if (found)
	        			continue;
        		}
        		
        		if (offlineNotification && config.getNotifyClose() && config.getClosingPositions() != null)
        		{
        			for (String pos : config.getClosingPositions())
            		{
                        if (controller.getCallsign().startsWith(pos))
                        {
                            sendLogoutNotification(controller, config);
                            break;
                        }
            		}
        		}
        		if (!offlineNotification && config.getOpeningPositions() != null)
        		{
        			for (String pos : config.getOpeningPositions())
            		{
                        if (controller.getCallsign().startsWith(pos))
                        {
                        	sendNotification(controller, config);
                            break;
                        }
            		}
        		}
        	}
        }
    }

    /**
     * @description Send an online controller notification to the designated channel.
     * @param c Controller who has gone online.
     * @param config Config for specific zone.
     */
    private void sendNotification(Controller c, NotifyConfig config) {
        try 
        {
            TextChannel notifyChannel = api.getTextChannelById(getStringOrDefault(config.getChannelID(), NeoConfigFile.getConfig().getChannelID()));
            if (notifyChannel != null)
            {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("" + c.getCallsign());
                eb.setDescription(getStringOrDefault(config.getOpeningText(), NeoConfigFile.getConfig().getOpeningText())
                        .replace("%name%", c.getRealName())
                        .replace("%position%", c.getCallsign())
                        .replace("%frequency%", c.getFrequency()));
                eb.setFooter("VATSIM", api.getSelfUser().getEffectiveAvatarUrl());
                eb.setColor(Color.GREEN);
                String mentionId = getStringOrDefault(config.getMentionID(), NeoConfigFile.getConfig().getMentionID());
                if (mentionId != null && !mentionId.isEmpty())
                	notifyChannel.sendMessage(mentionId).embed(eb.build()).queue();
                else
                	notifyChannel.sendMessage(eb.build()).queue();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * @description Send an offline controller notification to the designated channel.
     * @param c Controller who has gone offline.
     * @param config Config for specific zone.
     */
    private void sendLogoutNotification(Controller c, NotifyConfig config){
        try
        {
        	if (!config.getNotifyClose())
        		return;
        	
            TextChannel notifyChannel = api.getTextChannelById(getStringOrDefault(config.getChannelID(), NeoConfigFile.getConfig().getChannelID()));
            if (notifyChannel != null)
            {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle("" + c.getCallsign());
                eb.setDescription(getStringOrDefault(config.getClosingText(), NeoConfigFile.getConfig().getClosingText())
                        .replace("%name%", c.getRealName())
                        .replace("%position%", c.getCallsign())
                        .replace("%frequency%", c.getFrequency()));
                eb.setFooter("VATSIM", api.getSelfUser().getEffectiveAvatarUrl());
                eb.setColor(Color.RED);
                String mentionId = getStringOrDefault(config.getMentionID(), NeoConfigFile.getConfig().getMentionID());
                if (mentionId != null && !mentionId.isEmpty())
                	notifyChannel.sendMessage(mentionId).embed(eb.build()).queue();
                else
                	notifyChannel.sendMessage(eb.build()).queue();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
    
    /**
     * @description Check if str is not null nor empty.
     * @param str Input String
     * @param defaultStr Default String
     * @return Returns str if it's not null nor empty, otherwise returns defaultStr
     */
    private String getStringOrDefault(String str, String defaultStr)
    {
    	if (str == null || str.isEmpty())
    		return defaultStr;
    	return str;
    }
}

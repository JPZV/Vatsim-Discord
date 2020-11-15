package ml.jordie.vatsimnotify.vatsim.model;

import com.google.gson.annotations.SerializedName;

public class GlobalConfig extends GenericConfig
{
	public static final String DEFAULT_DATA_SOURCE = "http://us.data.vatsim.net/vatsim-data.txt";
	
	@SerializedName("discord_token")
	private String _discordToken;
	@SerializedName("data_source")
	private String _dataSource = DEFAULT_DATA_SOURCE;
	@SerializedName("zones")
	private NotifyConfig[] _zones;
	
    /**
     * @description This will save the settings for the bot
     */
    public GlobalConfig()
    {
    }

    public String getDiscordToken()
    {
        return this._discordToken;
    }
    public void setDiscordToken(String value)
    {
    	this._discordToken = value;
    }

    public String getDataSource()
    {
    	if(this._dataSource == null || this._dataSource.isEmpty())
    		return DEFAULT_DATA_SOURCE;
    	
        return this._dataSource;
    }
    public void setDataSource(String value)
    {
    	this._dataSource = value;
    }

    public NotifyConfig[] getZones()
    {
        return this._zones;
    }
    public void setZones(NotifyConfig[] value)
    {
    	this._zones = value;
    }
}

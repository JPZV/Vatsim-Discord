## Vatsim Discord

This is a Redux project from [VatsimNotify](https://github.com/JordannDev/Vatsim-Notify) which allows a Discord Server to be able to recieve notifications when a controller logs into VATSIM.
Filtering is also supported, so you will only see alerts for specific airports/facilities.

**Notice**
```
This is not an API of any sort, I would highly recommend against using this in another application. 
Most of this project is incomplete (specifically the Controller model). It works as it is, if you modify it, it may not work the same or at all.
```

## Configuration file

| Setting | Description |
| ------- | :---------: |
| DISCORD_TOKEN | The Bot token you want the Discord bot to login [with](https://discordapi.com).|
| POSITION_OPEN_NOTIFICATION | The message you want to be sent when a controller connects to the VATSIM network|
| POSITION_CLOSED_NOTIFICATION | The message you want to be sent when a controller who was online goes offline (optional) |
| CLOSING_NOTIFICATIONS | Enable (by setting it to yes or no) controller logoff notifications. |
| POSITIONS | Positions you want the discord bot to announce (example: BAIRES, SBCW, BOS, KC) |
| FILTER_POSITIONS | Enable position filtering (yes or no) |
| TEXT_CHANNEL | The channel id of the channel you want the bot to use.
| VATSIM_DATA_SOURCE | The VATSIM data server you want to use (status.vatsim.net for more info) 

<hr>

## Setup
```
You will need Java 14 (or higher) for this to work. 

Create a Bot "account" at https://discordapi.com (Google it if you don't know how).

Run the jar file (usually java -jar Filename.jar)
On the first run it will give you an error, but the configuration file will have generated.

Edit the config.properties file which was generated (add your Discord Token, and Text Channel)
You can learn more about the options above.

To get a Text Channel ID make sure you have "Developer mode" on Discord enabled, you can enable it
by going to Settings -> Appearance, and enabling Developer mode, then right click the channel you want to use
and click "Copy ID", put that in the TEXT_CHANNEL option in the config.properties file.

Now you can run the Bot.

You then need to add the Bot to your Discord server, you can look this up on Google, its not hard.
```

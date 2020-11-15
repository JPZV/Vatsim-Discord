
## Vatsim Discord

This is a Redux project from [VatsimNotify](https://github.com/JordannDev/Vatsim-Notify) which allows a Discord Server to be able to recieve notifications when a controller logs into VATSIM.
Filtering is also supported, so you will only see alerts for specific airports/facilities.

**Notice**
```
This is not an API of any sort, I would highly recommend against using this in another application. 
Most of this project is incomplete (specifically the Controller model). It works as it is, if you modify it, it may not work the same or at all.
```

## Configuration file

In order to make this bot work, you have to create/edit the `config.json` file. If you were using VatsimNotify, you have to do this anyway, as the `config.properties` file was deprecated and it's no longer in use.
The new configuration system works using JSON and it has more customization than before.

**Global configuration:**
| Setting | Type | Description |
| ------- | :---------: | :---------: |
| **discord_token** | String | The Bot token you want the Discord bot to login [with](https://discordapi.com).|
| **channel_id** | String | The channel id of the channel you want the bot to use.
| **mention_id** | String (optional) | The role ID (using `<@&ID>` format) or `@here` or `@everyone`
| **closing_text** | String | The message you want to be sent when a controller who was online goes offline. Available placeholders: `%name%` (of controller), `%position%` (AKA: Callsign), and `%frequency%`.
| **opening_text** | String | The message you want to be sent when a controller connects to the VATSIM network. Available placeholders: `%name%` (of controller), `%position%` (AKA: Callsign), and `%frequency%`.
| **zones** | Object array | Array with every zone that you want to define (please refer **Zone configuration** below).
<hr>

**Zone configuration:**
| Setting | Type | Description |
| ------- | :---------: | :---------: |
| **channel_id** | String (optional) | The channel id of the channel you want the bot to use for this zone. If it's missing or empty, the bot will use the global **channel_id** instead.
| **mention_id** | String (optional) | The role ID (using `<@&ID>` format) or `@here` or `@everyone` for this zone. If it's missing or empty, the bot will use the global **mention_id** instead.
| **closing_text** | String (optional) | The message you want to be sent when a controller who was online goes offline in this zone. Available placeholders: `%name%` (of controller), `%position%` (AKA: Callsign), and `%frequency%`. If it's missing or empty, the bot will use the global **closing_text** instead.
| **opening_text** | String | The message you want to be sent when a controller connects to the VATSIM network in this zone. Available placeholders: `%name%` (of controller), `%position%` (AKA: Callsign), and `%frequency%`. If it's missing or empty, the bot will use the global **opening_text** instead.
| **positions** | String array (optional) | Array with every callsign which the bot will look for in both for opening and closing controllers.
| **opening_positions** | String array (optional) | Array with every callsign which the bot will look for in just opening controllers.
| **closing_positions** | String array (optional) | Array with every callsign which the bot will look for in just closing controllers.
| **notify_all** | Boolean (optional) | If it's `true`, the bot will send a message for every controller regardless of **positions**, **opening_positions** and **closing_positions**. Default value: `false`.
| **notify_close** | Boolean (optional) | If it's `true`, the bot will send a message if there is a controller closing its frecuency and its callsign is specified in **positions**, or **closing_positions**, or if **notify_all** is `true`. Default value: `false`.
<hr>

 **Sample config:**
```json
{
    "discord_token": "[YOUR BOT TOKEN]",
    "channel_id": "775005707372199936",
    "mention_id": "@here",
    "closing_text": "%name% is closed %position%.",
    "opening_text": "%name% is opening %position% (%frequency%)",
    "zones": [
        {
            "mention_id": "<@&777603225347817503>",
            "closing_text": "%name% está cerrando %position%.",
            "opening_text": "%name% está abriendo %position% (%frequency%)",
            "positions": ["SCEL", "SCEZ", "SAEZ", "BAIRES", "SAME"],
            "notify_close": true
        },
        {
            "channel_id": "777595739215364146",
            "notify_all": true,
            "notify_close": true
        }
    ]
}

```


## Setup
**You will need Java 14 (or higher) for this to work.**

1) Create a Bot "account" at https://discordapi.com (Google it if you don't know how).
2) You then need to add the Bot to your Discord server, you can look this up on Google, its not hard.
3) Go to [release](https://github.com/JPZV/Vatsim-Discord/releases) and download the latest version's zip. Decompress it in a directory. Don't lose that directory as there will live the bot.
4) Edit the `config.json` file (you can rename sample-config.json to config.json and use its content as a guide). You can learn more about the options above.
5) Run the jar file (usually `java -jar Vatsim-Discord.jar`)

To get a Text Channel ID make sure you have "Developer mode" on Discord enabled, you can enable it
by going to Settings -> Appearance, and enabling Developer mode, then right click the channel you want to use
and click "Copy ID", put that in the **channel_id** option in the `config.json` file.
For the **mention_id** it's the same but using the roles. Remember that you have to put it as `<@&ID>` (for example: `<@&777603225347817503>`)

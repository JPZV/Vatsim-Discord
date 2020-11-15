package ml.jordie.vatsimnotify.vatsim.model;

import com.google.gson.annotations.SerializedName;

public class GenericConfig
{
	@SerializedName("channel_id")
	private String _channelID;
	@SerializedName("mention_id")
	private String _mentionID;
	@SerializedName("closing_text")
	private String _closingText;
	@SerializedName("opening_text")
	private String _openingText;

    /**
     * @description This will save the generic settings
     */
    public GenericConfig()
    {
    }

    public String getChannelID()
    {
    	if (this._channelID == null || this._channelID.isEmpty())
    		return null;
        return this._channelID;
    }
    public void setChannelID(String value)
    {
    	this._channelID = value;
    }

    public String getMentionID()
    {
        return this._mentionID;
    }
    public void setMentionID(String value)
    {
    	this._mentionID = value;
    }

    public String getClosingText()
    {
        return this._closingText;
    }
    public void setClosingText(String value)
    {
    	this._closingText = value;
    }

    public String getOpeningText()
    {
        return this._openingText;
    }
    public void setOpeningText(String value)
    {
    	this._openingText = value;
    }
}

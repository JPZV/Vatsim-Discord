package ml.jordie.vatsimnotify.vatsim.model;

import com.google.gson.annotations.SerializedName;

public class NotifyConfig extends GenericConfig
{
	@SerializedName("notify_all")
	private Boolean _notifyAll;
	@SerializedName("notify_close")
	private Boolean _notifyClose;
	@SerializedName("positions")
	private String[] _positions;
	@SerializedName("opening_positions")
	private String[] _openingPositions;
	@SerializedName("closing_positions")
	private String[] _closingPositions;

    /**
     * @description This will save the settings for notifications
     */
    public NotifyConfig()
    {
    }

    public Boolean getNotifyAll()
    {
        return this._notifyAll;
    }
    public void setNotifyAll(Boolean value)
    {
    	this._notifyAll = value;
    }

    public Boolean getNotifyClose()
    {
        return this._notifyClose;
    }
    public void setNotifyClose(Boolean value)
    {
    	this._notifyClose = value;
    }

    public String[] getPositions()
    {
        return this._positions;
    }
    public void setPositions(String[] value)
    {
    	this._positions = value;
    }
    
    public String[] getOpeningPositions()
    {
        return this._openingPositions;
    }
    public void setOpeningPositions(String[] value)
    {
    	this._openingPositions = value;
    }
    
    public String[] getClosingPositions()
    {
        return this._closingPositions;
    }
    public void setClosingPositions(String[] value)
    {
    	this._closingPositions = value;
    }
}

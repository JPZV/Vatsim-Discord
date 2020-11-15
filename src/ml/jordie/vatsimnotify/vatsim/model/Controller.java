package ml.jordie.vatsimnotify.vatsim.model;

import ml.jordie.vatsimnotify.vatsim.model.enums.ClientType;
import ml.jordie.vatsimnotify.vatsim.model.enums.FacilityType;

import java.util.ArrayList;

public class Controller {

    String callsign;
    String cid;
    String realName;
    ClientType clientType;
    String frequency;
    String latitude;
    String longitude;
    String altitude;
    String server;
    String protrevision;
    String ratingId;
    FacilityType facilityType;
    String visualRange;
    String atisMessage;
    String timeLastAtisReceived;
    String timeLogon;

    /**
     * @param dataString Vatsim Data Tag
     * @description Will format a string from VATSIM's Status file into a model
     * to be used across the program. Adding methods like controller.getFrequency() or controller.getOnlineTime()
     */
    public Controller(String dataString) {
        if (dataString.length() < 50)
            return;

        if (dataString.contains(":PILOT:"))
            return;
        
        String[] cInfo = dataString.split(":");
        ArrayList<String> cArray = new ArrayList<>();

        for (String s : cInfo) {
            if (!s.equalsIgnoreCase("")) {
                cArray.add(s);
            }
        }

        if (!cArray.get(3).equals("ATC"))
            return;

        if (cArray.get(0).endsWith("_OBS"))
            return;

        if(cArray.size() <= 10)
            return;

        callsign = cArray.get(0);
        cid = cArray.get(1);
        realName = cArray.get(2);
        clientType = (cArray.get(3).equalsIgnoreCase("ATC") ? ClientType.ATC : ClientType.PILOT);
        frequency = cArray.get(4);
        latitude = cArray.get(5);
        longitude = cArray.get(6);
        altitude = cArray.get(7);
        server = cArray.get(9);
        protrevision = cArray.get(10);
        ratingId = cArray.get(11);

        switch (cArray.get(12)) {
            case "0":
                facilityType = FacilityType.Observer;
            case "1":
                facilityType = FacilityType.Flight_Service_Station;
            case "2":
                facilityType = FacilityType.Clearance_Delivery;
            case "3":
                facilityType = FacilityType.Ground_Control;
            case "4":
                facilityType = FacilityType.Local_Control;
            case "5":
                facilityType = FacilityType.Approach_Departure;
            case "6":
                facilityType = FacilityType.Enroute;
            default:
                facilityType = FacilityType.Observer;
        }

        visualRange = cArray.get(13);
        atisMessage = cArray.get(14);
    }

    public String getCallsign() {
        return callsign.replace(" ", "").toUpperCase();
    }

    public String getCid() {
        return cid;
    }

    public String getRealName() {
        return realName;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public String getServer() {
        return server;
    }

    public String getProtrevision() {
        return protrevision;
    }

    public String getRatingId() {
        return ratingId;
    }

    public FacilityType getFacilityType() {
        return facilityType;
    }

    public String getVisualRange() {
        return visualRange;
    }

    public String getAtisMessage() {
        return atisMessage;
    }

    public String getTimeLastAtisReceived() {
        return timeLastAtisReceived;
    }

    public String getTimeLogon() {
        return timeLogon;
    }

}

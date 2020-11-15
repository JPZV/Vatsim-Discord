package ml.jordie.vatsimnotify.vatsim;

import ml.jordie.vatsimnotify.Bot;
import ml.jordie.vatsimnotify.vatsim.model.Controller;

import java.util.ArrayList;
import java.util.List;

public class ControllerManager {

    public static ControllerManager instance;
    // List of current controllers.
    public ArrayList<Controller> controllers = new ArrayList<>();
    // List of controller callsigns, used to iterate through the online list and remove controllers who have logged off.
    String newControllerCallsigns = "";

    public ControllerManager() {
        instance = this;
    }

    public static ControllerManager getInstance() {
        return instance;
    }

    /**
     * @param controller Controller which should be added to the array.
     * @descriptions Validates controller, and adds them to the array.
     */
    public void addController(Controller controller) {
        if (!controller.getCallsign().contains("ATIS")) {
            if (controller.getCallsign().endsWith("_SUP"))
                return;

            if (controller.getCallsign().matches("ATM|DATM|TA|EC|FE|WM"))
                return;

            if (!controller.getCallsign().contains("_"))
                return;

            newControllerCallsigns = newControllerCallsigns + "|" + controller.getCallsign();

            if (doesControllerExist(controller)) {
                Controller cc = getControllerByCallsign(controller.getCallsign());
                controllers.remove(cc);
                controllers.add(controller);
            } else {
                System.out.println("New Callsign: " + controller.getCallsign());
                controllers.add(controller);
                Bot.getInstance().newControllerAlert(controller, false);
            }
        }

    }

    /**
     * @param c Controller
     * @return boolean
     * @description Will check to see if the array contains a controller with the callsign you provided (by passing a Controller model).
     */
    public boolean doesControllerExist(Controller c) {
        for (Controller ctrl : controllers) {
            if (ctrl.getCallsign().equalsIgnoreCase(c.getCallsign())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @description This method will use a list of online controller callsigns to
     * see if any callsign (controller) has logged off, if so, it will remove them from the list.
     */
    public void purgeControllers() {
        List<Controller> toRemove = new ArrayList<>();
        for (Controller oldC : controllers) {
            if (!oldC.getCallsign().matches(newControllerCallsigns)) {
                System.out.println(oldC.getCallsign() + " logged off of the VATSIM network.");
                Bot.getInstance().newControllerAlert(oldC, true);
                toRemove.add(oldC);
            }
        }

        controllers.removeAll(toRemove);
        newControllerCallsigns = "";
    }

    /**
     * @param callsign The callsign of the controller you want to have returned.
     * @return ml.jordie.vatsimnotify.vatsim.model.Controller
     * @description Finds controller by callsign, and will return.
     */
    public Controller getControllerByCallsign(String callsign) {
        for (Controller c : controllers)
            if (c.getCallsign().equalsIgnoreCase(callsign))
                return c;
        return null;
    }

    /**
     * @param name The name of the controller you want to have returned.
     * @return ml.jordie.vatsimnotify.vatsim.model.Controller
     * @description Finds controller by name, and will return.
     */
    public Controller getControllerByName(String name) {
        for (Controller c : controllers)
            if (c.getRealName().equalsIgnoreCase(name))
                return c;
        return null;
    }

    /**
     * @param name
     * @description Removes controller from the array (by callsign).
     */
    public void removeController(String name) {
        for (Controller c : controllers)
            if (c.getRealName().equalsIgnoreCase(name))
                controllers.remove(c);
    }

    /**
     * @return ArrayList
     * @description Returns the array of controllers.
     */
    public ArrayList<Controller> getControllers() {
        return controllers;
    }
}

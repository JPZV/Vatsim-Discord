package ml.jordie.vatsimnotify.vatsim;

import ml.jordie.vatsimnotify.storage.NeoConfigFile;
import ml.jordie.vatsimnotify.vatsim.model.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class Parser {

    /**
     * @description Get the VATSIM-DATA.txt, and parse it.
     */
    public void parse() {
        try {
            URL url = new URL(NeoConfigFile.getConfig().getDataSource());
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            ArrayList<String> lines = new ArrayList<>();

            String line = br.readLine();
            while (!(line = br.readLine()).equalsIgnoreCase("!SERVERS:"))
                if (!line.startsWith(";") || !line.startsWith("!"))
                    lines.add(line);

            for (String s : lines) {
                if (s.contains(":ATC:")) {
                    Controller ctrl = new Controller(s);
                    if (ctrl.getCid() != null)
                        ControllerManager.getInstance().addController(ctrl);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}

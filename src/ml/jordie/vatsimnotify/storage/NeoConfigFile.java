package ml.jordie.vatsimnotify.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

import ml.jordie.vatsimnotify.vatsim.model.GlobalConfig;

public class NeoConfigFile {

    public static NeoConfigFile instance = null;
    
    private GlobalConfig _globalConfig;
    
    public NeoConfigFile() {
    	if (instance != null)
    		return;
        instance = this;
        try
        {
            File file = new File("config.json");
            if (!file.exists())
            	throw new FileNotFoundException("config.json is missing in the current directory. Please go to https://github.com/JPZV/Vatsim-Discord for more information.");
            
            _globalConfig = new Gson().fromJson(new FileReader("config.json"), GlobalConfig.class);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            System.exit(-1);
        }
    }

    public static NeoConfigFile getInstance() {
        return instance;
    }
    
    public GlobalConfig getGlobalConfig()
    {
        return this._globalConfig;
    }
    
    public static GlobalConfig getConfig()
    {
    	if (instance == null)
    		new NeoConfigFile();
    	
        return instance._globalConfig;
    }
}

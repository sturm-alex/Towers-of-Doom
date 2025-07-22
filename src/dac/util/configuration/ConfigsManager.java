package dac.util.configuration;

public class ConfigsManager {
    private static ConfigsManager instance = new ConfigsManager();


    private ConfigsManager() {}


    public static ConfigsManager getInstance() { return instance; }


    public Config loadConfig( String configName ) {
        // gets a config from the configs folder
        return null;
    }
}

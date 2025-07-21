package dac.util.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class NConfig {
    private static final NConfig instance = new NConfig( "configs/" );

    private final String configPath;


    private NConfig( String configPath )
    {
        this.configPath = configPath;
    }


    public static NConfig getInstance()
    {
        return instance;
    }


    private Config loadConfig( Class<? extends Config> configClass ) {
        try {
            String fileName = configClass.getSimpleName() + ".json";
            // System.out.println("Loading config: " + fileName);
            return new ObjectMapper().readValue(
                Paths.get(this.configPath, fileName).toFile(),
                configClass
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config: " + configClass.getSimpleName(), e);
        }
    }


    public Map<String, Config> loadAllConfigs(Map<String, Class<? extends Config>> configClasses ) {
        Map<String, Config> configs = new HashMap<>();
        java.nio.file.Path dir = Paths.get(this.configPath);
        
        try ( java.nio.file.DirectoryStream<java.nio.file.Path> stream =
                java.nio.file.Files.newDirectoryStream(dir, "*.json") )
        {
            
            for (java.nio.file.Path entry : stream) {
                String fileName = entry.getFileName().toString();
                if (fileName.toUpperCase().endsWith(".JSON")) {
                    String name = fileName.substring(0, fileName.length() - 5); // remove ".json"
                    Class<? extends Config> configClass = configClasses.get(name);
                    if (configClass != null) {
                        Config config = loadConfig(configClass);
                        configs.put(name, config);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load configs", e);
        }
        return configs;
    }


    public void saveAllConfigs(Map<String, Config> configs )
    {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("Saving configs to: " + this.configPath);

        try
        {
            java.nio.file.Path dir = Paths.get(this.configPath);
            System.out.println("Config directory: " + dir.toAbsolutePath());

            // Ensure the directory exists
            if (Files.notExists(dir))
            {
                Files.createDirectories(dir);
            }

            for (Map.Entry<String, Config> entry : configs.entrySet())
            {
                String name = entry.getKey();
                Config config = entry.getValue();

                java.nio.file.Path filePath = dir.resolve(name + ".json");
                System.out.println("Saving config: " + filePath);
                // Write the Config object as JSON into the file
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(filePath.toFile(), config);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to save configs", e);
        }
    }

}

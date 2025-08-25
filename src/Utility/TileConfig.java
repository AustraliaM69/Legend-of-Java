package Utility;

import java.io.*;
import java.util.*;

public class TileConfig {
    
    private static final String CONFIG_FILE = "res/tiles.conf";
    private static Map<String, TileInfo> tileConfigs = new HashMap<>();
    
    public static class TileInfo {
        public String name;
        public int sheetX, sheetY;
        public boolean collision;
        public String description;
        
        public TileInfo(String name, int sheetX, int sheetY, boolean collision, String description) {
            this.name = name;
            this.sheetX = sheetX;
            this.sheetY = sheetY;
            this.collision = collision;
            this.description = description;
        }
    }
    
    /**
     * Loads tile configuration from file
     */
    public static void loadConfig() {
        try {
            File configFile = new File(CONFIG_FILE);
            if (!configFile.exists()) {
                createDefaultConfig();
            }
            
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String line;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip comments and empty lines
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String name = parts[0].trim();
                    int sheetX = Integer.parseInt(parts[1].trim());
                    int sheetY = Integer.parseInt(parts[2].trim());
                    boolean collision = Boolean.parseBoolean(parts[3].trim());
                    String description = parts.length > 4 ? parts[4].trim() : "";
                    
                    tileConfigs.put(name, new TileInfo(name, sheetX, sheetY, collision, description));
                }
            }
            reader.close();
            
        } catch (IOException e) {
            System.err.println("Error loading tile config: " + e.getMessage());
        }
    }
    
    /**
     * Creates a default configuration file
     */
    private static void createDefaultConfig() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE));
            writer.println("# Tile Configuration File");
            writer.println("# Format: name,sheetX,sheetY,collision,description");
            writer.println("# Example: grass00,0,0,false,Basic grass tile");
            writer.println();
            writer.println("grass00,0,0,false,Basic grass tile");
            writer.println("grass01,1,0,false,Grass tile variant");
            writer.println("water00,2,0,true,Water tile");
            writer.println("wall,1,3,true,Solid wall");
            writer.close();
            
        } catch (IOException e) {
            System.err.println("Error creating default config: " + e.getMessage());
        }
    }
    
    /**
     * Gets tile info by name
     */
    public static TileInfo getTileInfo(String name) {
        return tileConfigs.get(name);
    }
    
    /**
     * Gets all tile configurations
     */
    public static Map<String, TileInfo> getAllTiles() {
        return new HashMap<>(tileConfigs);
    }
    
    /**
     * Adds a new tile configuration
     */
    public static void addTile(String name, int sheetX, int sheetY, boolean collision, String description) {
        tileConfigs.put(name, new TileInfo(name, sheetX, sheetY, collision, description));
        saveConfig();
    }
    
    /**
     * Saves configuration to file
     */
    private static void saveConfig() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE));
            writer.println("# Tile Configuration File");
            writer.println("# Format: name,sheetX,sheetY,collision,description");
            writer.println();
            
            for (TileInfo info : tileConfigs.values()) {
                writer.println(String.format("%s,%d,%d,%s,%s", 
                    info.name, info.sheetX, info.sheetY, info.collision, info.description));
            }
            writer.close();
            
        } catch (IOException e) {
            System.err.println("Error saving config: " + e.getMessage());
        }
    }
}

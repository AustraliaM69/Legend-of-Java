package Utility;

import java.io.*;
import java.util.*;

public class TiledPropertiesGenerator {
    
    public static void main(String[] args) {
        generateTiledProperties();
    }
    
    public static void generateTiledProperties() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("res/tiled_properties.txt"));
            
            writer.println("# Tiled Properties Setup Guide");
            writer.println("# Copy these properties for each tile in Tiled");
            writer.println();
            
            // Define tile properties based on TileManager
            String[][] tileData = {
                // Row 0
                {"grass00", "0", "false", "Basic grass tile"},
                {"grass01", "1", "false", "Grass tile variant"},
                {"water00", "2", "true", "Water tile"},
                {"water01", "3", "true", "Water tile"},
                {"water02", "4", "true", "Water tile"},
                {"water03", "5", "true", "Water tile"},
                {"water04", "6", "true", "Water tile"},
                {"water05", "7", "true", "Water tile"},
                {"water06", "8", "true", "Water tile"},
                {"water07", "9", "true", "Water tile"},
                {"water08", "10", "true", "Water tile"},
                {"water09", "11", "true", "Water tile"},
                
                // Row 1
                {"water10", "12", "true", "Water tile"},
                {"water11", "13", "true", "Water tile"},
                {"water12", "14", "true", "Water tile"},
                {"water13", "15", "true", "Water tile"},
                {"road00", "16", "false", "Road tile"},
                {"road01", "17", "false", "Road tile"},
                {"road02", "18", "false", "Road tile"},
                {"road03", "19", "false", "Road tile"},
                {"road04", "20", "false", "Road tile"},
                {"road05", "21", "false", "Road tile"},
                {"road06", "22", "false", "Road tile"},
                {"road07", "23", "false", "Road tile"},
                
                // Row 2
                {"road08", "24", "false", "Road tile"},
                {"road09", "25", "false", "Road tile"},
                {"road10", "26", "false", "Road tile"},
                {"road11", "27", "false", "Road tile"},
                {"road12", "28", "false", "Road tile"},
                {"earth", "29", "false", "Earth tile"},
                {"floor01", "30", "false", "Floor tile"},
                {"wall", "31", "true", "Solid wall"},
                {"tree", "32", "true", "Tree obstacle"},
                {"hut", "33", "true", "Building"},
                {"table01", "34", "true", "Table object"},
                {"stone", "35", "true", "Rock formation"},
                
                // Row 3
                {"sand", "36", "false", "Sandy ground"},
                {"lava", "37", "true", "Hot lava"},
                {"ice", "38", "false", "Slippery ice"},
                {"bridge", "39", "false", "Bridge"},
                {"door", "40", "true", "Door"},
                {"chest", "41", "true", "Treasure chest"},
                {"sign", "42", "false", "Sign post"}
            };
            
            writer.println("# Tile Properties for Tiled");
            writer.println("# Format: tile_name, tile_id, collision, description");
            writer.println();
            
            for (String[] tile : tileData) {
                writer.println(String.format("# %s (ID: %s, Collision: %s)", 
                    tile[0], tile[1], tile[2]));
                writer.println(String.format("tile_id: %s", tile[1]));
                writer.println(String.format("collision: %s", tile[2]));
                writer.println(String.format("description: %s", tile[3]));
                writer.println();
            }
            
            writer.println("# Tiled Setup Instructions:");
            writer.println("# 1. Open Tiled and create new tileset");
            writer.println("# 2. Set tile size to 16x16");
            writer.println("# 3. Load tilesheet.png");
            writer.println("# 4. For each tile, add the properties above");
            writer.println("# 5. Save tileset and create map");
            
            writer.close();
            System.out.println("Tiled properties file generated: res/tiled_properties.txt");
            
        } catch (IOException e) {
            System.err.println("Error generating Tiled properties: " + e.getMessage());
        }
    }
}

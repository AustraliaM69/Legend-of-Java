package Utility;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class AutoTileManager {
    
    private static final String RES_DIR = "res";
    private static final String TILESHEET_PATH = "res/tilesheet.png";
    private static final String CONFIG_PATH = "res/auto_tiles.conf";
    private static final String TILED_PROPERTIES_PATH = "res/tiled_properties.txt";
    private static final String TILEMANAGER_TEMPLATE_PATH = "res/tilemanager_template.java";
    
    private static final int TILE_SIZE = 16;
    private static final int SHEET_COLS = 12;
    private static final int SHEET_ROWS = 6;
    
    public static void main(String[] args) {
        System.out.println("=== Auto Tile Manager ===");
        scanAndUpdateTiles();
    }
    
    public static void scanAndUpdateTiles() {
        try {
            // 1. Scan for tile images
            List<String> tileNames = scanForTileImages();
            System.out.println("Found " + tileNames.size() + " tile images");
            
            // 2. Generate tile sheet
            generateTileSheet(tileNames);
            
            // 3. Generate configuration files
            generateConfigFiles(tileNames);
            
            // 4. Generate TileManager code
            generateTileManagerCode(tileNames);
            
            System.out.println("✅ All files updated successfully!");
            System.out.println("📝 Copy the generated code from res/tilemanager_template.java to src/Tiles/TileManager.java");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static List<String> scanForTileImages() {
        List<String> tileNames = new ArrayList<>();
        File resDir = new File(RES_DIR);
        
        if (resDir.exists() && resDir.isDirectory()) {
            File[] files = resDir.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".png") && 
                !name.equals("tilesheet.png") && 
                !name.equals("player_spritesheet.png") &&
                !name.equals("player_spritesheet_info.txt"));
            
            if (files != null) {
                for (File file : files) {
                    String name = file.getName().replace(".png", "");
                    tileNames.add(name);
                }
            }
        }
        
        // Sort tiles for consistent ordering
        Collections.sort(tileNames);
        return tileNames;
    }
    
    private static void generateTileSheet(List<String> tileNames) {
        try {
            // Use the existing TileSheetGenerator
            TileSheetGenerator.generateTileSheet(RES_DIR, TILESHEET_PATH);
            System.out.println("✅ Tile sheet generated: " + TILESHEET_PATH);
        } catch (Exception e) {
            System.err.println("❌ Error generating tile sheet: " + e.getMessage());
        }
    }
    
    private static void generateConfigFiles(List<String> tileNames) {
        try {
            // Generate auto_tiles.conf
            generateAutoConfig(tileNames);
            
            // Generate tiled_properties.txt
            generateTiledProperties(tileNames);
            
            System.out.println("✅ Configuration files generated");
        } catch (Exception e) {
            System.err.println("❌ Error generating config files: " + e.getMessage());
        }
    }
    
    private static void generateAutoConfig(List<String> tileNames) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_PATH));
        
        writer.println("# Auto-generated tile configuration");
        writer.println("# This file is automatically updated by AutoTileManager");
        writer.println("# DO NOT EDIT MANUALLY - your changes will be overwritten!");
        writer.println();
        
        for (int i = 0; i < tileNames.size(); i++) {
            String tileName = tileNames.get(i);
            boolean collision = determineCollision(tileName);
            String description = generateDescription(tileName);
            
            writer.println(String.format("%s,%d,%d,%s,%s", 
                tileName, 
                i % SHEET_COLS, 
                i / SHEET_COLS, 
                collision, 
                description));
        }
        
        writer.close();
    }
    
    private static void generateTiledProperties(List<String> tileNames) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(TILED_PROPERTIES_PATH));
        
        writer.println("# Auto-generated Tiled properties");
        writer.println("# Copy these properties for each tile in Tiled");
        writer.println();
        
        for (int i = 0; i < tileNames.size(); i++) {
            String tileName = tileNames.get(i);
            boolean collision = determineCollision(tileName);
            String description = generateDescription(tileName);
            
            writer.println(String.format("# %s (ID: %d, Collision: %s)", tileName, i, collision));
            writer.println(String.format("tile_id: %d", i));
            writer.println(String.format("collision: %s", collision));
            writer.println(String.format("description: %s", description));
            writer.println();
        }
        
        writer.close();
    }
    
    private static void generateTileManagerCode(List<String> tileNames) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(TILEMANAGER_TEMPLATE_PATH));
        
        writer.println("// Auto-generated TileManager code");
        writer.println("// Copy this to src/Tiles/TileManager.java");
        writer.println();
        writer.println("package Tiles;");
        writer.println();
        writer.println("import Main.GamePanel;");
        writer.println("import Utility.TileSheetGenerator;");
        writer.println("import java.awt.*;");
        writer.println("import java.awt.image.BufferedImage;");
        writer.println("import java.io.IOException;");
        writer.println("import javax.imageio.ImageIO;");
        writer.println();
        writer.println("public class TileManager {");
        writer.println();
        writer.println("    GamePanel gp;");
        writer.println("    Tile[] tile;");
        writer.println("    TileSheet tileSheet;");
        writer.println("    int[][] mapTileNum;");
        writer.println();
        writer.println("    public TileManager(GamePanel gp){");
        writer.println("        this.gp = gp;");
        writer.println("        tile = new Tile[" + (tileNames.size() + 10) + "]; // Auto-sized");
        writer.println("        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];");
        writer.println("        tileSheet = new TileSheet(\"/tilesheet.png\", 16, " + SHEET_COLS + ", " + SHEET_ROWS + ");");
        writer.println("        getTileImage();");
        writer.println("        loadMap();");
        writer.println("    }");
        writer.println();
        writer.println("    public void getTileImage(){");
        
        // Generate tile definitions
        for (int i = 0; i < tileNames.size(); i++) {
            String tileName = tileNames.get(i);
            boolean collision = determineCollision(tileName);
            int col = i % SHEET_COLS;
            int row = i / SHEET_COLS;
            
            writer.println(String.format("        tile[%d] = new Tile(%d, %d, %s); // %s", 
                i, col, row, collision, tileName));
        }
        
        writer.println("    }");
        writer.println();
        writer.println("    public void loadMap() {");
        writer.println("        // TODO: Load from Tiled map file");
        writer.println("        for (int col = 0; col < gp.maxScreenCol; col++) {");
        writer.println("            for (int row = 0; row < gp.maxScreenRow; row++) {");
        writer.println("                mapTileNum[col][row] = 0; // Default to grass");
        writer.println("            }");
        writer.println("        }");
        writer.println("    }");
        writer.println();
        writer.println("    public void draw(Graphics2D g2){");
        writer.println("        int col = 0;");
        writer.println("        int row = 0;");
        writer.println("        int x = 0;");
        writer.println("        int y = 0;");
        writer.println();
        writer.println("        while(col < gp.maxScreenCol && row < gp.maxScreenRow){");
        writer.println("            int tileNum = mapTileNum[col][row];");
        writer.println("            if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null) {");
        writer.println("                BufferedImage tileImage = tileSheet.getTile(tile[tileNum].sheetX, tile[tileNum].sheetY);");
        writer.println("                if (tileImage != null) {");
        writer.println("                    g2.drawImage(tileImage, x, y, gp.tileSize, gp.tileSize, null);");
        writer.println("                }");
        writer.println("            }");
        writer.println("            col++;");
        writer.println("            x += gp.tileSize;");
        writer.println("            if(col == gp.maxScreenCol){");
        writer.println("                col = 0;");
        writer.println("                x = 0;");
        writer.println("                row++;");
        writer.println("                y += gp.tileSize;");
        writer.println("            }");
        writer.println("        }");
        writer.println("    }");
        writer.println("}");
        
        writer.close();
    }
    
    private static boolean determineCollision(String tileName) {
        // Auto-detect collision based on tile name
        String lowerName = tileName.toLowerCase();
        
        // Tiles that should have collision
        if (lowerName.contains("wall") || 
            lowerName.contains("tree") || 
            lowerName.contains("hut") || 
            lowerName.contains("table") ||
            lowerName.contains("stone") ||
            lowerName.contains("lava") ||
            lowerName.contains("door") ||
            lowerName.contains("chest") ||
            lowerName.contains("water")) {
            return true;
        }
        
        // Tiles that should NOT have collision
        if (lowerName.contains("grass") || 
            lowerName.contains("road") || 
            lowerName.contains("earth") ||
            lowerName.contains("floor") ||
            lowerName.contains("sand") ||
            lowerName.contains("ice") ||
            lowerName.contains("bridge") ||
            lowerName.contains("sign")) {
            return false;
        }
        
        // Default to no collision for unknown tiles
        return false;
    }
    
    private static String generateDescription(String tileName) {
        String lowerName = tileName.toLowerCase();
        
        if (lowerName.contains("grass")) return "Grass tile";
        if (lowerName.contains("water")) return "Water tile";
        if (lowerName.contains("road")) return "Road tile";
        if (lowerName.contains("wall")) return "Solid wall";
        if (lowerName.contains("tree")) return "Tree obstacle";
        if (lowerName.contains("hut")) return "Building";
        if (lowerName.contains("table")) return "Table object";
        if (lowerName.contains("stone")) return "Rock formation";
        if (lowerName.contains("earth")) return "Earth tile";
        if (lowerName.contains("floor")) return "Floor tile";
        if (lowerName.contains("sand")) return "Sandy ground";
        if (lowerName.contains("lava")) return "Hot lava";
        if (lowerName.contains("ice")) return "Slippery ice";
        if (lowerName.contains("bridge")) return "Bridge";
        if (lowerName.contains("door")) return "Door";
        if (lowerName.contains("chest")) return "Treasure chest";
        if (lowerName.contains("sign")) return "Sign post";
        
        return tileName + " tile";
    }
}

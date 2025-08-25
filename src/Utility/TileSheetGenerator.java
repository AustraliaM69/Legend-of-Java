package Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Comparator;

public class TileSheetGenerator {
    
    private static final int TILE_SIZE = 16; // Original tile size
    private static final int SHEET_COLS = 12; // Increased to accommodate more tiles
    private static final int SHEET_ROWS = 6; // Increased to accommodate more tiles
    
    // Tile mapping - maps tile names to their position in the sheet
    public static final Map<String, Integer> TILE_MAPPING = new HashMap<>();
    
    // Define the order we want tiles to appear in the sheet
    private static final String[] TILE_ORDER = {
        // Grass tiles
        "grass00", "grass01",
        // Water tiles
        "water00", "water01", "water02", "water03", "water04", "water05",
        "water06", "water07", "water08", "water09", "water10", "water11", "water12", "water13",
        // Road tiles
        "road00", "road01", "road02", "road03", "road04", "road05",
        "road06", "road07", "road08", "road09", "road10", "road11", "road12",
        // Other tiles
        "earth", "floor01", "wall", "tree", "hut", "table01",
        // New tiles - add your new tiles here:
        "stone", "sand", "lava", "ice", "bridge", "door", "chest", "sign"
    };
    
    static {
        // Initialize tile mapping based on the order
        for (int i = 0; i < TILE_ORDER.length; i++) {
            TILE_MAPPING.put(TILE_ORDER[i], i);
        }
    }
    
    /**
     * Generates a tile sheet from individual tile images
     * @param inputDir Directory containing individual tile images
     * @param outputPath Path where the tile sheet should be saved
     */
    public static void generateTileSheet(String inputDir, String outputPath) {
        try {
            int sheetWidth = SHEET_COLS * TILE_SIZE;
            int sheetHeight = SHEET_ROWS * TILE_SIZE;
            
            BufferedImage tileSheet = new BufferedImage(sheetWidth, sheetHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = tileSheet.createGraphics();
            
            // Set background to transparent
            g2d.setComposite(AlphaComposite.Clear);
            g2d.fillRect(0, 0, sheetWidth, sheetHeight);
            g2d.setComposite(AlphaComposite.SrcOver);
            
            File inputDirectory = new File(inputDir);
            File[] tileFiles = inputDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));
            
            if (tileFiles == null) {
                System.err.println("No PNG files found in directory: " + inputDir);
                return;
            }
            
            // Sort files to match our desired order
            Arrays.sort(tileFiles, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    String name1 = f1.getName().replace(".png", "");
                    String name2 = f2.getName().replace(".png", "");
                    
                    int index1 = -1, index2 = -1;
                    for (int i = 0; i < TILE_ORDER.length; i++) {
                        if (TILE_ORDER[i].equals(name1)) index1 = i;
                        if (TILE_ORDER[i].equals(name2)) index2 = i;
                    }
                    
                    if (index1 == -1 && index2 == -1) return name1.compareTo(name2);
                    if (index1 == -1) return 1;
                    if (index2 == -1) return -1;
                    return Integer.compare(index1, index2);
                }
            });
            
            int tileIndex = 0;
            for (File tileFile : tileFiles) {
                String tileName = tileFile.getName().replace(".png", "");
                
                // Skip player spritesheet and other non-tile files
                if (tileName.equals("player_spritesheet") || tileName.equals("player_spritesheet_info")) {
                    continue;
                }
                
                if (tileIndex >= SHEET_COLS * SHEET_ROWS) {
                    System.err.println("Warning: More tiles than can fit in sheet. Stopping at " + tileIndex);
                    break;
                }
                
                try {
                    BufferedImage tileImage = ImageIO.read(tileFile);
                    if (tileImage != null) {
                        int col = tileIndex % SHEET_COLS;
                        int row = tileIndex / SHEET_COLS;
                        int x = col * TILE_SIZE;
                        int y = row * TILE_SIZE;
                        
                        g2d.drawImage(tileImage, x, y, TILE_SIZE, TILE_SIZE, null);
                        System.out.println("Added tile: " + tileFile.getName() + " at position (" + col + ", " + row + ") - ID: " + tileIndex);
                        tileIndex++;
                    }
                } catch (IOException e) {
                    System.err.println("Error reading tile file: " + tileFile.getName());
                    e.printStackTrace();
                }
            }
            
            g2d.dispose();
            
            // Save the tile sheet
            File outputFile = new File(outputPath);
            ImageIO.write(tileSheet, "PNG", outputFile);
            System.out.println("Tile sheet generated successfully: " + outputPath);
            System.out.println("Total tiles added: " + tileIndex);
            
        } catch (IOException e) {
            System.err.println("Error generating tile sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gets the tile ID from the mapping
     * @param tileName Name of the tile
     * @return Tile ID, or -1 if not found
     */
    public static int getTileId(String tileName) {
        return TILE_MAPPING.getOrDefault(tileName, -1);
    }
    
    /**
     * Gets the tile name from the ID
     * @param tileId ID of the tile
     * @return Tile name, or null if not found
     */
    public static String getTileName(int tileId) {
        if (tileId >= 0 && tileId < TILE_ORDER.length) {
            return TILE_ORDER[tileId];
        }
        return null;
    }
    
    /**
     * Gets the position of a tile in the sheet
     * @param tileId ID of the tile
     * @return Point with x,y coordinates in the sheet, or null if not found
     */
    public static Point getTilePosition(int tileId) {
        if (tileId < 0 || tileId >= SHEET_COLS * SHEET_ROWS) {
            return null;
        }
        
        int col = tileId % SHEET_COLS;
        int row = tileId / SHEET_COLS;
        return new Point(col, row);
    }
    
    public static void main(String[] args) {
        // Example usage
        String inputDir = "res";
        String outputPath = "res/tilesheet.png";
        generateTileSheet(inputDir, outputPath);
    }
}

package Tiles;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TileSheet {
    
    private BufferedImage sheet;
    private int tileSize;
    private int sheetCols;
    private int sheetRows;
    
    public TileSheet(String path, int tileSize, int sheetCols, int sheetRows) {
        this.tileSize = tileSize;
        this.sheetCols = sheetCols;
        this.sheetRows = sheetRows;
        loadSheet(path);
    }
    
    private void loadSheet(String path) {
        try {
            // Try loading from file system first (for res directory)
            if (path.startsWith("/")) {
                path = path.substring(1); // Remove leading slash
            }
            String fullPath = "res/" + path;
            System.out.println("Trying to load tile sheet from: " + new java.io.File(fullPath).getAbsolutePath());
            sheet = ImageIO.read(new java.io.File(fullPath));
            
            // If that fails, try loading from classpath
            if (sheet == null) {
                System.out.println("File loading failed, trying classpath: /" + path);
                sheet = ImageIO.read(getClass().getResourceAsStream("/" + path));
            }
            
            if (sheet == null) {
                System.err.println("Failed to load tile sheet: " + path);
            } else {
                System.out.println("Successfully loaded tile sheet: " + path);
            }
        } catch (IOException e) {
            System.err.println("Error loading tile sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Gets a tile image from the sheet at the specified position
     * @param col Column in the sheet (0-based)
     * @param row Row in the sheet (0-based)
     * @return BufferedImage of the tile, or null if invalid coordinates
     */
    public BufferedImage getTile(int col, int row) {
        if (sheet == null || col < 0 || col >= sheetCols || row < 0 || row >= sheetRows) {
            return null;
        }
        
        int x = col * tileSize;
        int y = row * tileSize;
        
        return sheet.getSubimage(x, y, tileSize, tileSize);
    }
    
    /**
     * Gets a tile image from the sheet using tile ID
     * @param tileId ID of the tile (0-based)
     * @return BufferedImage of the tile, or null if invalid ID
     */
    public BufferedImage getTile(int tileId) {
        if (tileId < 0 || tileId >= sheetCols * sheetRows) {
            return null;
        }
        
        int col = tileId % sheetCols;
        int row = tileId / sheetCols;
        return getTile(col, row);
    }
    
    /**
     * Gets the entire tile sheet image
     * @return BufferedImage of the entire sheet
     */
    public BufferedImage getSheet() {
        return sheet;
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
    public int getSheetCols() {
        return sheetCols;
    }
    
    public int getSheetRows() {
        return sheetRows;
    }
}

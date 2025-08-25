package Tiles;

import java.awt.image.BufferedImage;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;
    
    // Tile sheet coordinates
    public int sheetX;
    public int sheetY;
    
    // Constructor for tile sheet system
    public Tile(int sheetX, int sheetY, boolean collision) {
        this.sheetX = sheetX;
        this.sheetY = sheetY;
        this.collision = collision;
    }
    
    // Constructor for individual image system (for backward compatibility)
    public Tile() {
        this.collision = false;
    }
}

package Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    
    private BufferedImage spriteSheet;
    private int spriteWidth;
    private int spriteHeight;
    
    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        loadSpriteSheet(path);
    }
    
    private void loadSpriteSheet(String path) {
        try {
            // Try loading from file system first
            String filePath = path.startsWith("/") ? path.substring(1) : path;
            spriteSheet = ImageIO.read(new java.io.File(filePath));
            
            if (spriteSheet == null) {
                // Try as resource
                spriteSheet = ImageIO.read(getClass().getResourceAsStream(path));
            }
            if (spriteSheet == null) {
                // Try with leading slash removed
                spriteSheet = ImageIO.read(getClass().getResourceAsStream(path.substring(1)));
            }
            
            if (spriteSheet == null) {
                System.err.println("Could not load sprite sheet: " + path);
            } else {
                System.out.println("Successfully loaded sprite sheet: " + path);
            }
        } catch (IOException e) {
            System.err.println("Error loading sprite sheet: " + path);
            e.printStackTrace();
        }
    }
    
    public BufferedImage getSprite(int row, int col) {
        if (spriteSheet == null) {
            return null;
        }
        
        int x = col * spriteWidth;
        int y = row * spriteHeight;
        
        return spriteSheet.getSubimage(x, y, spriteWidth, spriteHeight);
    }
    
    public BufferedImage getSprite(int index) {
        if (spriteSheet == null) {
            return null;
        }
        
        int spritesPerRow = spriteSheet.getWidth() / spriteWidth;
        int row = index / spritesPerRow;
        int col = index % spritesPerRow;
        
        return getSprite(row, col);
    }
    
    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }
    
    public int getSpriteWidth() {
        return spriteWidth;
    }
    
    public int getSpriteHeight() {
        return spriteHeight;
    }
}

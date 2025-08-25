package Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteSheetGenerator {
    
    public static void generatePlayerSpriteSheet() {
        int spriteSize = 16;
        int rows = 5; // Changed to 5 rows to include walk up animations
        int cols = 4;
        int width = spriteSize * cols;
        int height = spriteSize * rows;
        
        BufferedImage spriteSheet = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = spriteSheet.createGraphics();
        
        // Set rendering hints for better quality
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Generate sprites for each row
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int x = col * spriteSize;
                int y = row * spriteSize;
                
                // Draw background (transparent)
                g2.setColor(new Color(0, 0, 0, 0));
                g2.fillRect(x, y, spriteSize, spriteSize);
                
                // Draw sprite based on row type
                switch (row) {
                    case 0: // Idle animations
                        drawIdleSprite(g2, x, y, spriteSize, col);
                        break;
                    case 1: // Walk down animations
                        drawWalkDownSprite(g2, x, y, spriteSize, col);
                        break;
                    case 2: // Walk left animations
                        drawWalkLeftSprite(g2, x, y, spriteSize, col);
                        break;
                    case 3: // Walk right animations
                        drawWalkRightSprite(g2, x, y, spriteSize, col);
                        break;
                    case 4: // Walk up animations
                        drawWalkUpSprite(g2, x, y, spriteSize, col);
                        break;
                }
            }
        }
        
        g2.dispose();
        
        // Save the sprite sheet
        try {
            File outputFile = new File("res/player_spritesheet.png");
            outputFile.getParentFile().mkdirs(); // Create directories if they don't exist
            ImageIO.write(spriteSheet, "PNG", outputFile);
            System.out.println("Player sprite sheet generated successfully at: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving sprite sheet: " + e.getMessage());
        }
    }
    
    private static void drawIdleSprite(Graphics2D g2, int x, int y, int size, int frame) {
        // Simple character shape for idle animation
        g2.setColor(Color.BLUE);
        g2.fillOval(x + 4, y + 2, 8, 8); // Head
        
        g2.setColor(Color.RED);
        g2.fillRect(x + 6, y + 10, 4, 6); // Body
        
        // Simple frame variation
        if (frame % 2 == 0) {
            g2.setColor(Color.YELLOW);
            g2.fillOval(x + 5, y + 3, 2, 2); // Eye
        }
    }
    
    private static void drawWalkDownSprite(Graphics2D g2, int x, int y, int size, int frame) {
        g2.setColor(Color.BLUE);
        g2.fillOval(x + 4, y + 2, 8, 8); // Head
        
        g2.setColor(Color.RED);
        g2.fillRect(x + 6, y + 10, 4, 6); // Body
        
        // Walking animation - legs moving
        g2.setColor(Color.BLACK);
        if (frame % 2 == 0) {
            g2.fillRect(x + 5, y + 16, 2, 4); // Left leg
        } else {
            g2.fillRect(x + 9, y + 16, 2, 4); // Right leg
        }
    }
    
    private static void drawWalkLeftSprite(Graphics2D g2, int x, int y, int size, int frame) {
        g2.setColor(Color.BLUE);
        g2.fillOval(x + 4, y + 2, 8, 8); // Head
        
        g2.setColor(Color.RED);
        g2.fillRect(x + 6, y + 10, 4, 6); // Body
        
        // Walking left animation
        g2.setColor(Color.BLACK);
        if (frame % 2 == 0) {
            g2.fillRect(x + 2, y + 16, 2, 4); // Left leg
        } else {
            g2.fillRect(x + 6, y + 16, 2, 4); // Right leg
        }
    }
    
    private static void drawWalkRightSprite(Graphics2D g2, int x, int y, int size, int frame) {
        g2.setColor(Color.BLUE);
        g2.fillOval(x + 4, y + 2, 8, 8); // Head
        
        g2.setColor(Color.RED);
        g2.fillRect(x + 6, y + 10, 4, 6); // Body
        
        // Walking right animation
        g2.setColor(Color.BLACK);
        if (frame % 2 == 0) {
            g2.fillRect(x + 8, y + 16, 2, 4); // Right leg
        } else {
            g2.fillRect(x + 4, y + 16, 2, 4); // Left leg
        }
    }
    
    private static void drawWalkUpSprite(Graphics2D g2, int x, int y, int size, int frame) {
        g2.setColor(Color.BLUE);
        g2.fillOval(x + 4, y + 2, 8, 8); // Head
        
        g2.setColor(Color.RED);
        g2.fillRect(x + 6, y + 10, 4, 6); // Body
        
        // Walking up animation - different leg movement pattern
        g2.setColor(Color.BLACK);
        if (frame % 2 == 0) {
            g2.fillRect(x + 5, y + 16, 2, 4); // Left leg
        } else {
            g2.fillRect(x + 9, y + 16, 2, 4); // Right leg
        }
        
        // Add some visual distinction for walking up (like raised arms)
        g2.setColor(Color.YELLOW);
        if (frame % 2 == 0) {
            g2.fillRect(x + 3, y + 8, 2, 3); // Left arm
        } else {
            g2.fillRect(x + 11, y + 8, 2, 3); // Right arm
        }
    }
    
    public static void main(String[] args) {
        generatePlayerSpriteSheet();
    }
}

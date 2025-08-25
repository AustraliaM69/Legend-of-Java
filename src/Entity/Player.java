package Entity;

import Main.GamePanel;
import Main.KeyHandler;
import Utility.SpriteSheet;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    
    // Animation arrays for different directions
    private BufferedImage[] idleSprites;
    private BufferedImage[] walkDownSprites;
    private BufferedImage[] walkLeftSprites;
    private BufferedImage[] walkRightSprites;
    private BufferedImage[] walkUpSprites;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        //Halfway through the screen.
        screenX = gp.screenWidth / 2 -(gp.tileSize/2);
        screenY = gp.screenHeight / 2 -(gp.tileSize/2);

        setDefaultValues();
        loadSprites();
    }
    
    public void setDefaultValues(){
        //start pos
        worldX = 100;
        worldY = 100;
        speed = 4;
        direction = "down";
        moving = false;
    }
    
    public void loadSprites() {
        // Initialize sprite arrays first to prevent null pointer exceptions
        idleSprites = new BufferedImage[4];
        walkDownSprites = new BufferedImage[4];
        walkLeftSprites = new BufferedImage[4];
        walkRightSprites = new BufferedImage[4];
        walkUpSprites = new BufferedImage[4];
        
        System.out.println("Sprite arrays initialized. idleSprites is null: " + (idleSprites == null));
        
        try {
            // Load sprite sheet - adjust path and sprite size as needed
            SpriteSheet spriteSheet = new SpriteSheet("/res/characterSheet.png", 16, 16);
            
            // Check if sprite sheet loaded successfully
            if (spriteSheet.getSpriteSheet() == null) {
                System.err.println("Failed to load sprite sheet, using fallback graphics");
                return;
            }

            
            // Load idle animations (row 0)
            for (int i = 0; i < 4; i++) {
                idleSprites[i] = spriteSheet.getSprite(0, i);
            }
            
            // Load walk down animations (row 1)
            for (int i = 0; i < 4; i++) {
                walkDownSprites[i] = spriteSheet.getSprite(1, i);
            }
            
            // Load walk left animations (row 2)
            for (int i = 0; i < 4; i++) {
                walkLeftSprites[i] = spriteSheet.getSprite(2, i);
            }
            
            // Load walk right animations (row 3)
            for (int i = 0; i < 4; i++) {
                walkRightSprites[i] = spriteSheet.getSprite(3, i);
            }
            
            // Load walk up animations (row 4)
            for (int i = 0; i < 4; i++) {
                walkUpSprites[i] = spriteSheet.getSprite(4, i);
            }
            
            // Set initial sprite
            if (idleSprites[0] != null) {
                sprite = idleSprites[0];
            }
            
        } catch (Exception e) {
            System.err.println("Error loading player sprites: " + e.getMessage());
            // Fallback to colored rectangle if sprites fail to load
        }
    }
    
    public void update(){
        moving = false;
        
        if(keyH.upPressed == true){
            direction = "up";
            moving = true;
            worldY -= speed;
        }
        if(keyH.downPressed == true){
            direction = "down";
            moving = true;
            worldY += speed;
        }
        if(keyH.leftPressed == true){
            direction = "left";
            moving = true;
            worldX -= speed;
        }
        if(keyH.rightPressed == true){
            direction = "right";
            moving = true;
            worldX += speed;
        }
        
        // Update animation
        updateAnimation();
    }
    
    public void updateAnimation() {
        // Check if sprites are loaded
        if (idleSprites == null || walkDownSprites == null || walkLeftSprites == null || 
            walkRightSprites == null || walkUpSprites == null) {
            return; // Use fallback graphics
        }
        
        animationCounter++;
        
        if (animationCounter > animationSpeed) {
            if (spriteNum < 3) {
                spriteNum++;
            } else {
                spriteNum = 0;
            }
            animationCounter = 0;
        }
        
        // Select appropriate sprite based on direction and movement
        if (moving) {
            switch (direction) {
                case "down":
                    if (walkDownSprites[spriteNum] != null) {
                        sprite = walkDownSprites[spriteNum];
                    }
                    break;
                case "up":
                    if (walkUpSprites[spriteNum] != null) {
                        sprite = walkUpSprites[spriteNum];
                    }
                    break;
                case "left":
                    if (walkLeftSprites[spriteNum] != null) {
                        sprite = walkLeftSprites[spriteNum];
                    }
                    break;
                case "right":
                    if (walkRightSprites[spriteNum] != null) {
                        sprite = walkRightSprites[spriteNum];
                    }
                    break;
            }
        } else {
            // Idle animation
            if (idleSprites[spriteNum] != null) {
                sprite = idleSprites[spriteNum];
            }
        }
    }
    
    public void draw(Graphics2D g2){
        if (sprite != null) {
            // Draw the sprite scaled to tile size
            g2.drawImage(sprite, screenX, screenY, gp.tileSize, gp.tileSize, null);
        } else {
            // Fallback to colored rectangle if sprite is null
            g2.setColor(Color.WHITE);
            g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        }
    }
}

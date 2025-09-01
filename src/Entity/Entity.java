package Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {

    public int worldX,worldY;
    public int speed;
    
    // Sprite and animation properties
    public BufferedImage sprite;
    public BufferedImage[] sprites;
    public int spriteCounter = 0;
    public int spriteNum = 0;
    public int animationSpeed = 10; // Lower = faster animation
    public int animationCounter = 0;
    // Direction for animations
    public String direction = "down"; // down, up, left, right
    public boolean moving = false;

    //Hitbox
    public Rectangle solidArea;
    public boolean collisionOn = false;

}

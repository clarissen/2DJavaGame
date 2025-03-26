package entity;

import java.awt.image.BufferedImage;

// stores variables used in all player and non-player classes
public class Entity{

    public int x, y;
    public int speed;

    // use BufferedImage to store image files
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
}
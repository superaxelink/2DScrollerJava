package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import surrounding.Background;

public class Entity {
  
  public int worldX, worldY;
  public int x;
  public int y;
  public int baseVx;
  public int baseVy;
  public int vx;
  public int vy;
  public int jumpHeight;
  public int weight;
  public String direction;

  //COUNTERS
  public int frameCounter=0;

  public BufferedImage spriteSheet;
  public BufferedImage image;

  public Rectangle solidArea;

  public GamePanel gp;
  public Background background;
}
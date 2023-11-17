package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import entity.Entity;

public class UtilityTool {

  public boolean squareCollision(Entity obj1, Entity obj2){
    if( obj2.yEndSquare <= obj1.y ){return false;} //bottom1 above top2
    if(obj1.yEndSquare <= obj2.y ){return false;} //top1 below bottom2
    if(obj1.xEndSquare <= obj2.xStartSquare){return false;} //right1 before left2
    if( obj2.xEndSquare <= obj1.xStartSquare  ){return false;} //left1 past right2
    //If none of the sides from A are outside B
    return true;
  }

  public boolean squareCollision(int o1x1, int o1x2, int o1y1, int o1y2, 
    int o2x1, int o2x2, int o2y1, int o2y2){
    if(o2y2 <= o1y1){return false;} //bottom1 above top2
    if(o1y2 <= o2y1 ){return false;} //top1 above bottom2
    if(o1x2 <= o2x1){return false;} //right1 past left2
    if(o2x2 <= o1x1 ){return false;} //left1 past right2
    //If none of the sides from A are outside B
    return true;
  }

  //TO SCALE IMAGES BEFORE LOOP
  public BufferedImage scaleImage(BufferedImage original, int width, int height ){

    //SET ITS ORIGINAL TYPE
    //BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
    //ALLOW TRANSPARENT BACKGROUND
    BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = scaledImage.createGraphics();
    g2.drawImage(original, 0, 0, width, height, null);
    g2.dispose();

    return scaledImage;
  }

  public BufferedImage setup(String imagePath, int width, int height){

    BufferedImage image = null;

    try {
      image = ImageIO.read(getClass().getResourceAsStream(imagePath+ ".png"));
      image = scaleImage(image, width, height);
/*       System.out.println(image.getWidth() + " " + image.getHeight());s */
    } catch (Exception e) {
      image = null;
      /* e.printStackTrace(); */
    }
    return image;
  }

  public void mirrorImage(BufferedImage image, String path){
    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage mimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    File f = null;
    // Create mirror image pixel by pixel
    for (int y = 0; y < height; y++) {
      for (int lx = 0, rx = width - 1; lx < width; lx++, rx--) {
            
          // lx starts from the left side of the image
          // rx starts from the right side of the
          // image lx is used since we are getting
          // pixel from left side rx is used to set
          // from right side get source pixel value
          int p = image.getRGB(lx, y);

          // set mirror image pixel value
          mimg.setRGB(rx, y, p);
      }
    }
    // save mirror image
    try {
      f = new File(path);
      ImageIO.write(mimg, "png", f);
    }
    catch (Exception e) {
      System.out.println("Error: " + e);
    }
  }
  
}

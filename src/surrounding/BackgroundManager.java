package surrounding;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class BackgroundManager {

  //System
  GamePanel gp;

  //Dimensions
  public int width;
  public int height;

  //Coordinates
  public int x;
  public int y;

  //Properties
  public BufferedImage image1, image2, image3, image4, image5;
  public int speed;
  public int groundLine;

  public BackgroundManager(GamePanel gp){
    this.gp = gp;
  }

  public BufferedImage setup(String imagePath, int width, int height){

    UtilityTool uTool = new UtilityTool();
    BufferedImage image = null;
    //System.out.println(new File("../testGame/res/background/layerCity5.png"));
    try {
      image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
      image = uTool.scaleImage(image, width, height);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return image;
  }
}

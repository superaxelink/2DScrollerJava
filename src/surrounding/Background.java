package surrounding;

import main.GamePanel;

import java.awt.Graphics2D;

public class Background extends BackgroundManager {

  public Background(GamePanel gp){
    super(gp);

    setDefaultValues();
  }
  public void setDefaultValues(){
    width = 1667;
    height = 500;
    groundLine = gp.screenHeight-84;
    x=0;
    y=0;
    getImages();
  }
  public void getImages(){
    image1 = setup("/background/layerCity1", width, height);
    image2 = setup("/background/layerCity2", width, height);
    image3 = setup("/background/layerCity3", width, height);
    image4 = setup("/background/layerCity4", width, height);
    image5 = setup("/background/layerCity5", width, height);
  }
  public void draw(Graphics2D g2){
    g2.drawImage(image1, x, y, null);
    g2.drawImage(image2, x, y, null);
    g2.drawImage(image3, x, y, null);
    g2.drawImage(image4, x, y, null);
    g2.drawImage(image5, x, y, null);
  }
}

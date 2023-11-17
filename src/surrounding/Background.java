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
    g2.drawImage(image1, 0, 0, gp.screenWidth, gp.screenHeight, 
    x , 0, x + gp.screenWidth, 0 + gp.screenHeight, null);
    g2.drawImage(image2, 0, 0, gp.screenWidth, gp.screenHeight, 
    x , 0, x + gp.screenWidth, 0 + gp.screenHeight, null);
    g2.drawImage(image3, 0, 0, gp.screenWidth, gp.screenHeight, 
    x , 0, x + gp.screenWidth, 0 + gp.screenHeight, null);
    g2.drawImage(image4, 0, 0, gp.screenWidth, gp.screenHeight, 
    x , 0, x + gp.screenWidth, 0 + gp.screenHeight, null);
    g2.drawImage(image5, 0, 0, gp.screenWidth, gp.screenHeight, 
    x , 0, x + gp.screenWidth, 0 + gp.screenHeight, null);
  }

  public void update(){
    if(x+gp.screenWidth>=width){
      x=0;
    }else if(x<0){
      x=gp.screenWidth;
    }
  }

  public void reset(){
    x=0;
  }
}

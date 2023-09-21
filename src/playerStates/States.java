package playerStates;

import java.awt.image.BufferedImage;

import entity.Player;
import main.GamePanel;

public class States {

  GamePanel gp;
  Player player;

  //Main Image Characteristics
  public int spriteThreshold;
  public int totalSprites; 
  public int baseWidth; 
  public int baseHeight;

  public BufferedImage spriteSheet;
  public BufferedImage spriteSheetLeft;

  //SubImage characteristics
  public int width;
  public int height;
  public int dividedWidth;
  public int widthOffset;
  public int xStartSubimage;
  public int yStartSubimage; 

  public BufferedImage subImage;

  //counters
  public int spriteCounter;
  public int stateCounter = 0;
  public int frameCounter;


  public States(GamePanel gp, Player player){
    this.gp = gp;
    this.player = player;
  }

  public void changeState(int newState, String direction){
    player.currentState = player.stateList[newState];
    player.currentState.spriteCounter = 0; 
    player.currentState.xStartSubimage = 0;
    changeDirection(direction);
  }

  public void changeDirection(String direction){
    if(direction.equals("right")){
      player.currentState.xStartSubimage = player.currentState.width*player.currentState.spriteCounter + player.currentState.widthOffset;
      player.currentState.subImage = player.currentState.spriteSheet.getSubimage(player.currentState.xStartSubimage, player.currentState.yStartSubimage, player.currentState.width, player.currentState.height);
    }else if(direction.equals("left")){
      player.currentState.xStartSubimage = player.currentState.baseWidth - player.currentState.width*(player.currentState.spriteCounter+1) + player.currentState.widthOffset;
      player.currentState.subImage = player.currentState.spriteSheetLeft.getSubimage(player.currentState.xStartSubimage, player.currentState.yStartSubimage, player.currentState.width, player.currentState.height);
    }
  }

  public void inputHandler(String direction){}
  public void update(){}
}
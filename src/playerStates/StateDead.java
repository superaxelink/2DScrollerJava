package playerStates;

import entity.Player;
import main.GamePanel;

public class StateDead extends States{

  public StateDead(GamePanel gp, Player player) {
    //parent class constructor
    super(gp, player);
    
    //basic characteristics
    spriteThreshold=6;
    totalSprites = 3;
    baseWidth = 384;
    baseHeight = 128;
    dividedWidth = baseWidth/totalSprites;

    height = 73;
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Dead", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/DeadLeft", baseWidth, baseHeight);
  }
  
  public void update(){
    String direction = player.direction;
    frameCounter++;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      if(direction.equals("left")){
        if(spriteCounter>0){
          spriteCounter--;
        }
      }
      else if(direction.equals("right")){
        if(spriteCounter<totalSprites-1){
          spriteCounter++;
        }
      }
    }
  }

  public void inputHandler(){
    
  }
}

package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateIdle1 extends States{

  public StateIdle1(GamePanel gp, Player player){
    //parent class constructor
    super(gp, player);

    //basic characteristics
    spriteThreshold = 4;
    totalSprites = 8;
    baseWidth = 1024;
    baseHeight=128;
    dividedWidth = baseWidth/totalSprites;

    height = 73;
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;
    
    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Idle", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/IdleLeft", baseWidth, baseHeight);
  }

  public void update(){
    frameCounter++;
    String direction = player.direction;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      spriteCounter++;
    }
    if(spriteCounter>maxSprites){
      spriteCounter=0;
      stateCounter++;
    }
    changeDirection(direction);
    if(stateCounter>2){
      changeState(PlayerState.Idle_2.ordinal(), direction);
      stateCounter=0;
    }
  }

  public void inputHandler(String direction){
    if(gp.keyH.keys.size()!=0){
      if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
        changeState(PlayerState.Walk.ordinal(),direction);
        stateCounter=0;
      }
      if(gp.keyH.keys.contains("up")){
        changeState(PlayerState.Jump.ordinal(),direction);
        stateCounter=0;
      }
      if(gp.keyH.keys.contains("attack")){
        changeState(PlayerState.Attack_1.ordinal(),direction);
        stateCounter=0;
      }
    }
  }
}

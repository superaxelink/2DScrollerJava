package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateIdle2 extends States{

  public StateIdle2(GamePanel gp, Player player) {
    //parent class constructor
    super(gp, player);

    //basic characteristics
    spriteThreshold = 6;
    totalSprites = 3;
    baseWidth = 384;
    baseHeight=128;
    dividedWidth = baseWidth/totalSprites;

    height = 73;
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Idle_2", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/IdleLeft_2", baseWidth, baseHeight);
  }

  public void update(){
    String direction = player.direction;
    frameCounter++;
    if(spriteCounter<totalSprites-1){
      if(frameCounter>spriteThreshold){
        frameCounter=0;
        spriteCounter++;
      }
    }
    changeDirection(direction);
  }

  public void inputHandler(String direction){
    if(gp.keyH.keys.size()!=0){
      if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
        changeState(PlayerState.Walk.ordinal(),direction);
      }
      if(gp.keyH.keys.contains("up")){
        changeState(PlayerState.Jump.ordinal(),direction);
      }
      if(gp.keyH.keys.contains("attack")){
        changeState(PlayerState.Attack_1.ordinal(),direction);
      }
    }
  }
}

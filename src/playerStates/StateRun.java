package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateRun extends States{

  public StateRun(GamePanel gp, Player player) {
    super(gp, player);
    spriteThreshold = 5;
    totalSprites = 8 ;
    baseWidth = 1024;
    baseHeight=128;
    dividedWidth = baseWidth/totalSprites;

    height = 73; 
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Run", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/RunLeft", baseWidth, baseHeight);
  }

  public void update(){
    String direction = player.direction;
    frameCounter++;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      player.horizontalMovent(player.vx*3);
      spriteCounter++;
      if(spriteCounter>totalSprites-1){
        spriteCounter=0;
      }
    }
    changeDirection(direction);
  }
  
  public void inputHandler(String direction){
    if(gp.keyH.keys.size()!=0){
      if(gp.keyH.keys.contains("up")){
        changeState(PlayerState.Jump.ordinal(),direction);
        player.vx = player.baseVx; 
      }
      if(gp.keyH.keys.contains("attack")){
        changeState(PlayerState.Attack_1.ordinal(),direction);
        player.vx = player.baseVx; 
      }
      if(!gp.keyH.keys.contains("space") || 
          (
            (player.direction=="right" && !gp.keyH.keys.contains("right")) || 
            (player.direction=="left" && !gp.keyH.keys.contains("left"))
          ) 
        ){
        changeState(PlayerState.Walk.ordinal(),direction);
      }
    }else{
      changeState(PlayerState.Idle_1.ordinal(),direction);
      player.currentState = player.stateList[PlayerState.Idle_1.ordinal()]; 
      stateCounter = 0;
    }
  }
}

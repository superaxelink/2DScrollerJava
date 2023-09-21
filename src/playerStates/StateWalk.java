package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateWalk extends States{

  public StateWalk(GamePanel gp, Player player) {
    //parent class constructor
    super(gp, player);

    //basic characteristics
    spriteThreshold = 5;
    totalSprites = 8;
    baseWidth = 1024;
    baseHeight=128;
    dividedWidth = baseWidth/totalSprites;

    height = 76; 
    yStartSubimage = 52;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Walk", baseWidth, baseHeight);
/*     gp.uTool.mirrorImage(spriteSheet,"/home/axl/Documents/java/Games/testGame/res/player/ChibiMale/Swordsman/WalkLeft.png"); */
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/WalkLeft", baseWidth, baseHeight);
  }
  
  public void update(){
    frameCounter++;
    String direction = player.direction;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      player.horizontalMovent(player.vx);
      frameCounter=0;
      spriteCounter++;
      if(spriteCounter>maxSprites){
        spriteCounter=0;
      }
    }
    changeDirection(direction);
  }

  public void inputHandler(String direction){
    
    if(player.direction=="right" && !gp.keyH.keys.contains("right")){
      changeState(PlayerState.Idle_1.ordinal(),direction);
    }
    if(player.direction=="left" && !gp.keyH.keys.contains("left")){
      changeState(PlayerState.Idle_1.ordinal(),direction);
    }
    if(gp.keyH.keys.contains("up")){
      changeState(PlayerState.Jump.ordinal(),direction);
    }
    if((gp.keyH.keys.contains("space") && gp.keyH.keys.contains("right")) || (gp.keyH.keys.contains("space") && gp.keyH.keys.contains("left")) ){
      changeState(PlayerState.Run.ordinal(),direction);
    }
    if(gp.keyH.keys.contains("attack")){
      changeState(PlayerState.Attack_1.ordinal(),direction);
    }
  }
}

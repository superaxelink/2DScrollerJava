package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateAttack1 extends States{

  public StateAttack1(GamePanel gp, Player player) {
    super(gp, player);
    spriteThreshold=6;
    totalSprites = 6;
    baseWidth = 768;
    baseHeight = 128;
    dividedWidth = baseWidth/totalSprites;

    height = 75; 
    yStartSubimage = 53;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Attack_1", baseWidth, baseHeight);
/*     gp.uTool.mirrorImage(baseSpriteImage,"/home/axl/Documents/java/Games/testGame/res/player/ChibiMale/Swordsman/AttackLeft_1.png"); */
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/AttackLeft_1", baseWidth, baseHeight);
  }

  public void update(){
    frameCounter++;
    String direction = player.direction;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      frameCounter=0; 
      spriteCounter++;
    }
    if(spriteCounter>=maxSprites){
      if(gp.keyH.keys.contains("attack")){
        changeState(PlayerState.Attack_2.ordinal(),direction);
      }else{
        changeState(PlayerState.Idle_1.ordinal(),direction);
      }
      spriteCounter=0;
    }
    changeDirection(direction);
  }

  public void inputHandler(String direction){
    if(gp.keyH.keys.contains("up")){
      changeState(PlayerState.Jump.ordinal(),direction);
    }
    if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
      changeState(PlayerState.Walk.ordinal(),direction);
    }
  }
}

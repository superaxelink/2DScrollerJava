package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateAttack3 extends States{

  public StateAttack3(GamePanel gp, Player player) {
    super(gp, player);

    //spriteThreshold = 60;
    spriteThreshold=8;
    totalSprites = 4;
    baseWidth = 512;
    baseHeight = 128;
    dividedWidth = baseWidth/totalSprites;

    height = 75;
    yStartSubimage = 53;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Attack_3", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/AttackLeft_3", baseWidth, baseHeight);
  }

  public void update(){
    String direction = player.direction;
    frameCounter++;
    if(frameCounter>spriteThreshold){
      spriteCounter++;
      frameCounter=0;
    }
    if(spriteCounter>=totalSprites-1){
      if(gp.keyH.keys.contains("attack")){
        changeState(PlayerState.Attack_1.ordinal(),direction);
      }else{
        changeState(PlayerState.Idle_1.ordinal(),direction);
      }
      spriteCounter = 0;
    }
    changeDirection(direction);
  }

  public void inputHandler(String direction){
  }
}

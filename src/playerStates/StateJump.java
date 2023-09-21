package playerStates;

import entity.Player;
import entity.Player.PlayerState;
import main.GamePanel;

public class StateJump extends States{

  int jumpCounter = 0;

  //Try to generalize this state
  int startingJump = 2;
  int goingUp = 4;
  int onMaxHeight = 5;
  int falling = 5;
  int landing = 7;

  public StateJump(GamePanel gp, Player player) {
    super(gp, player);
    spriteThreshold=5;
    totalSprites = 8;
    baseWidth = 1024;
    baseHeight=128;
    dividedWidth = baseWidth/totalSprites;

    height = 73; 
    yStartSubimage = 55;

    widthOffset = 0;
    width = dividedWidth;

    spriteSheet = gp.uTool.setup("/player/ChibiMale/Swordsman/Jump", baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup("/player/ChibiMale/Swordsman/JumpLeft", baseWidth, baseHeight);
  }

  public void update(){
    frameCounter++;
    if(player.y>player.groundLine){
      player.y=player.groundLine;
    }
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      player.gravity();
      if(!player.onGround()){
        player.horizontalMovent(player.vx);
      }
      if(player.onGround() && spriteCounter<2){
        stateCounter++;
        if(stateCounter<5){
          spriteCounter++;
          stateCounter = 0;
        }
      }
      else if(spriteCounter<4){
        spriteCounter++;
        player.vy--; 
        player.y -=player.vy;
      }
      else if(spriteCounter==4){
        player.y-=player.vy;
        if(player.vy>0){
          player.vy--;
        }else{
          spriteCounter++;
        }
        if(gp.keyH.keys.contains("down")){
          spriteCounter++;
          player.vy = 0;
          stateCounter = 0;
        }
      }
      else if(spriteCounter==5){
        //player.x +=1;
        if(player.onGround()){
          player.vy = player.baseVy;
          spriteCounter++;
        }
      }
      else if(spriteCounter>5 && spriteCounter<7){
        stateCounter++;
        if(stateCounter<5){
          spriteCounter++;
          stateCounter = 0;
        }
      }
      else if(spriteCounter == 7){
        spriteCounter=0;
        player.currentState = player.stateList[PlayerState.Idle_1.ordinal()];
      }
    }
    changeDirection(player.direction);
  }

  public void inputHandler(){

  }
}

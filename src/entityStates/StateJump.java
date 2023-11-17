package entityStates;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateJump extends States{

  int jumpCounter = 0;

  public StateJump(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int startingJump, 
    int goingUp, int maxHeight, int falling, int landing, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft,
    int stateNumber, int speed,
    String type, String pathRight, String pathLeft) {
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    offsetHitBoxRight, offsetHitBoxLeft, stateNumber,
    type, pathRight, pathLeft);

    this.speed = speed; 

    this.startingJump = startingJump;
    this.goingUp = goingUp;
    this.maxHeight = maxHeight;
    this.falling = falling;
    this.landing = landing;
  }

  public void spriteUpdate(){
    frameCounter++;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      if(entity.onGround() && spriteCounter<goingUp){
        stateCounter++;
        if(stateCounter<5){
          spriteCounter++;
          stateCounter = 0;
        }
      }
      else if(goingUp<=spriteCounter && spriteCounter<falling){
        stateCounter++;
        if(stateCounter<5){
          stateCounter = 0;
          entity.y-=entity.vy;

          if(entity.vy - entity.weight>0){
            entity.vy--;
          }else{
            entity.vy=0;
            spriteCounter++;
          }
          if(gp.keyH.keys.contains("down") && type.equals("player")){
            spriteCounter++;
            entity.vy = 0;
            stateCounter = 0;
          }
        }
      }
      else if(falling==spriteCounter){
        if(entity.onGround()){
          spriteCounter++;
        }
      }
      else if(falling<spriteCounter && spriteCounter<landing){
        stateCounter++;
        if(stateCounter<spriteThreshold){
          spriteCounter++;
          stateCounter = 0;
        }
      }
      else if(landing<=spriteCounter && spriteCounter<totalSprites-1){
        spriteCounter=0;
        entity.vy = entity.baseVy;
        entity.changeState(StateList.Idle_1.ordinal(), entity.direction);
      }
      entity.gravity();
    }
    entity.changeDirection(entity.direction);
  }

  public void inputHandler(String direction){
    if(type.equals("player")){
      if(gp.keyH.keys.contains("attack")){
        entity.vy = entity.baseVy;
        entity.changeState(StateList.Attack_1.ordinal(),direction);
        stateCounter=0;
      }
    }
    else if(entity.type.equals("enemy")){
      double rand = Math.random();
      if(rand<0.33 && 0.34<rand){
        entity.x-=speed;
      }
      else if(0.66<rand && 0.67<rand){
        entity.x+=speed;
      }
    }
  }
}

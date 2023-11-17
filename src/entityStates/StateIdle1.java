package entityStates;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateIdle1 extends States{

  public StateIdle1(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft,
    int stateNumber,
    String type ,String pathRight, String pathLeft){
    //parent class constructor
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    offsetHitBoxRight, offsetHitBoxLeft,
    stateNumber,
    type, pathRight, pathLeft);
  }

  public void spriteUpdate(){
    String direction = entity.direction;
    frameCounter++;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      spriteCounter++;
    }
    if(spriteCounter>maxSprites){
      spriteCounter=0;
      stateCounter++;
    }
    entity.changeDirection(direction);
    if(stateCounter>2 && type.equals("player")){
      entity.changeState(StateList.Idle_2.ordinal(), direction);
      stateCounter=0;
    }
    if(type.equals("enemy")){
      randomDirection(direction);
    }
  }

  public void inputHandler(String direction){
    if(type.equals("player")){
      if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
        entity.changeState(StateList.Walk.ordinal(),direction);
        stateCounter=0;
      }
      else if(gp.keyH.keys.contains("up")){
        entity.changeState(StateList.Jump.ordinal(),direction);
        stateCounter=0;
      }
      else if(gp.keyH.keys.contains("attack")){
        entity.changeState(StateList.Attack_1.ordinal(),direction);
        stateCounter=0;
      }
    }
    else if(type.equals("enemy")){
      double rand = Math.random();
      StateList nextState = null;
      if(rand<0.01){
        nextState = StateList.Walk;
      }
      else if(0.11<rand && rand<0.12){
        nextState = StateList.Run;
      }
      else if(0.21<rand && rand<0.22){
        nextState = StateList.Jump;
      }
      else if(0.31<rand && rand<0.32){
        nextState = StateList.Attack_1;
      }
      else if(0.41<rand && rand<0.42){
        nextState = StateList.Attack_2;
      }
      else if(0.51<rand && rand<0.52){
        nextState = StateList.Attack_3;
      }
      if(nextState!=null && entity.stateList[nextState.ordinal()]!=null){
        entity.changeState(nextState.ordinal(), direction);
      }
    }
  }
}

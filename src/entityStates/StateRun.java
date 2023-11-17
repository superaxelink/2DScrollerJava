package entityStates;

import java.util.ArrayList;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateRun extends States{

  public StateRun(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int speed, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft, int stateNumber,
    String type, String pathRight, String pathLeft) {
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    offsetHitBoxRight, offsetHitBoxLeft, stateNumber,
    type, pathRight, pathLeft);

    this.speed = speed;
  }

  public void update(ArrayList<Entity> enemies){
    super.update(enemies);
    spriteUpdate();
  }

  public void update(Entity attacked){
    npcHorizontalMovement();
    spriteUpdate();
  }
  
  public void spriteUpdate(){
    String direction = entity.direction;
    frameCounter++;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      spriteCounter++;
      if(spriteCounter>totalSprites-1){
        spriteCounter=0;
      }
    }
    entity.changeDirection(direction);
  }

  public void inputHandler(String direction){
    if(type.equals("player")){
      if(gp.keyH.keys.isEmpty()) {
        entity.changeState(StateList.Idle_1.ordinal(), direction);
        entity.currentState = entity.stateList[StateList.Idle_1.ordinal()]; 
        stateCounter = 0;
        return; // Salir temprano si keys está vacío
      }
      if(gp.keyH.keys.contains("up")){
        entity.changeState(StateList.Jump.ordinal(),direction);
        entity.vx = entity.baseVx; 
      }
      else if(gp.keyH.keys.contains("attack")){
        entity.changeState(StateList.Attack_1.ordinal(),direction);
        entity.vx = entity.baseVx; 
      }
      else if(!gp.keyH.keys.contains("space") || 
          (
            (entity.direction=="right" && !gp.keyH.keys.contains("right")) || 
            (entity.direction=="left" && !gp.keyH.keys.contains("left"))
          ) 
        ){
        entity.changeState(StateList.Walk.ordinal(),direction);
      }
    }else if(type.equals("enemy")){
      double rand = Math.random();
      if(rand<0.01){
        entity.changeState(StateList.Walk.ordinal(), direction);
      }
      else if(0.11<rand && rand<0.12){
        entity.changeState(StateList.Idle_1.ordinal(), direction);
      }
      else if(0.21<rand && rand<0.22){
        entity.changeState(StateList.Jump.ordinal(), direction);
      }
      else if(0.31<rand && rand<0.32){
        entity.changeState(StateList.Attack_1.ordinal(), direction);
      }
      else if(0.41<rand && rand<0.42){
        entity.changeState(StateList.Attack_2.ordinal(), direction);
      }
      else if(0.51<rand && rand<0.52){
        entity.changeState(StateList.Attack_3.ordinal(), direction);
      }
    }
  }
}

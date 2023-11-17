package entityStates;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateIdle2 extends States{

  public StateIdle2(GamePanel gp, Entity entity,
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft,
    int stateNumber,
    String type, String pathRight, String pathLeft) {
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
    if(spriteCounter<totalSprites-1){
      if(frameCounter>spriteThreshold){
        frameCounter=0;
        spriteCounter++;
      }
    }
    entity.changeDirection(direction);
  }

  public void inputHandler(String direction){
    if(entity.type.equals("player")){
      if(gp.keyH.keys.contains("left") || gp.keyH.keys.contains("right")){
        entity.changeState(StateList.Walk.ordinal(),direction);
      }
      else if(gp.keyH.keys.contains("up")){
        entity.changeState(StateList.Jump.ordinal(),direction);
      }
      else if(gp.keyH.keys.contains("attack")){
        entity.changeState(StateList.Attack_1.ordinal(),direction);
      }
    }
  }
}

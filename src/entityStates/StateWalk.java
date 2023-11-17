package entityStates;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateWalk extends States{

  public StateWalk(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int speed, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft, int stateNumber,
    String type, String pathRight, String pathLeft) {
    //parent class constructor
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    offsetHitBoxRight, offsetHitBoxLeft, stateNumber,
    type, pathRight, pathLeft);

    this.speed = speed;
  }

  //NPC update method
  public void update(Entity attacked){
    super.update(attacked);
    npcHorizontalMovement();
  }

  public void spriteUpdate(){
    String direction = entity.direction;
    frameCounter++;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      spriteCounter++;
      if(spriteCounter>maxSprites){
        spriteCounter=0;
      }
    }
    entity.changeDirection(direction);
  }

  public void inputHandler(String direction){
    if(type.equals("player")){
      if(entity.direction.equals("right") && !gp.keyH.keys.contains("right")){
        entity.changeState(StateList.Idle_1.ordinal(),direction);
      }
      else if(entity.direction.equals("left") && !gp.keyH.keys.contains("left")){
        entity.changeState(StateList.Idle_1.ordinal(),direction);
      }
      else if(gp.keyH.keys.contains("up")){
        entity.changeState(StateList.Jump.ordinal(),direction);
      }
      else if((gp.keyH.keys.contains("space") && gp.keyH.keys.contains("right")) || (gp.keyH.keys.contains("space") && gp.keyH.keys.contains("left")) ){
        entity.changeState(StateList.Run.ordinal(),direction);
      }
      else if(gp.keyH.keys.contains("attack")){
        entity.changeState(StateList.Attack_1.ordinal(),direction);
      }
    }
    else if(type.equals("enemy")){
      double rand = Math.random();
      if(rand<0.01){
        entity.changeState(StateList.Idle_1.ordinal(), direction);
      }
      else if(0.11<rand && rand<0.12){
        entity.changeState(StateList.Jump.ordinal(), direction);
      }
      else if(0.21<rand && rand<0.22){
        entity.changeState(StateList.Attack_1.ordinal(), direction);
      }
      else if(0.31<rand && rand<0.32){
        entity.changeState(StateList.Attack_2.ordinal(), direction);
      }
      else if(0.41<rand && rand<0.42){
        entity.changeState(StateList.Attack_3.ordinal(), direction);
      }
    }
  }
}

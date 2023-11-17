package entityStates;

import java.util.ArrayList;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateHurt extends States{

  public StateHurt(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int widthHitBox, int offsetHitBox,
    int stateNumber,
    String type, String pathRight, String pathLeft) {
    //parent class constructor
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    0, 0, 
    stateNumber,
    type, pathRight, pathLeft);
  }
  
  public void update(ArrayList<Entity> enemies){
    spriteUpdate();
  }

  public void update(Entity attacked){
    spriteUpdate();
  }

  public void spriteUpdate(){
    frameCounter++;
    String direction = entity.direction;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      frameCounter=0;
      spriteCounter++;
    }
    if(spriteCounter>maxSprites){
      spriteCounter=0;
      stateCounter++;
      if(entity.type.equals("player") && entity.currentLife<=0){
        entity.changeState(StateList.Dead.ordinal(), direction);

      }else{
        entity.changeState(StateList.Idle_1.ordinal(), direction);
      }
    }
    entity.changeDirection(direction);
  }

  public void inputHandler(String direction){
  }
}

package entityStates;

import java.util.ArrayList;

import entity.Entity;
import main.GamePanel;

public class StateDead extends States{

  public StateDead(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int widthHitBox, int offsetHitBox,
    int stateNumber,
    String type, String pathRight, String pathLeft) {
    //parent class constructor
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    0, 0, stateNumber,
    type, pathRight, pathLeft);
  }
  
  public void update(ArrayList<Entity> enemies){
    spriteUpdate();
  }

  public void update(Entity attacked){
    spriteUpdate();
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

  public void inputHandler(){
    
  }
}

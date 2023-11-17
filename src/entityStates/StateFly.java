package entityStates;

import entity.Entity;
import main.GamePanel;

public class StateFly extends States{

  public StateFly(
    GamePanel gp, Entity entity, int spriteThreshold, 
    int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, 
    int widthHitBox, int offsetHitBoxRight, int offsetHitBoxLeft,
    int stateNumber, int speed,
    String type, String pathRight, String pathLeft) {
    super(gp, entity, spriteThreshold, totalSprites, baseWidth, baseHeight, height, yStartSubimage, widthOffset,
        widthHitBox, offsetHitBoxRight, offsetHitBoxLeft, stateNumber, type, pathRight, pathLeft);
    
    this.speed = speed;
  }
  
  public void update(Entity attacked){
    super.update(attacked);
    npcHorizontalMovement();
  }

  //NPC movement
  public void npcHorizontalMovement(){
    super.npcHorizontalMovement();
    int rand = (int) (20*Math.random()-10); 
    entity.x += rand;
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

  public void inputHandler(String direction){}
}

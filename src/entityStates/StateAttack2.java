package entityStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class StateAttack2 extends States{

  public StateAttack2(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight, 
    int height, int yStartSubimage, int widthOffset, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft,
    int widthAttack, int heightAttack, int xAttackHitboOffset, int yAttackHitboOffset,
    int spriteContact, int stateNumber, int attackDamage,
    String type, String pathRight, String pathLeft) {
    super(gp, entity,
    spriteThreshold, totalSprites, baseWidth, baseHeight,
    height, yStartSubimage, widthOffset, widthHitBox, 
    offsetHitBoxRight, offsetHitBoxLeft,
    stateNumber,
    type, pathRight, pathLeft);

    this.widthAttack = widthAttack;
    this.heightAttack = heightAttack;
    this.xAttackHitboOffset = xAttackHitboOffset;
    this.yAttackHitboOffset = yAttackHitboOffset;
    this.spriteContact = spriteContact;
    this.attackDamage = attackDamage;

    knockBackvx = 4;
  }

  public void draw(Graphics2D g2, int x, int y){
    super.draw(g2, x, y);
    if(spriteCounter>=spriteContact && gp.drawHitBox){
      entity.drawHitBox(g2, Color.YELLOW, xStartSquare, yStartSquare, widthAttack, heightAttack);
    }
  }

  //For the player
  public void update(ArrayList<Entity> enemies){
    super.update(enemies);
    updateHitboxAttack();
    playerAttackCollision(enemies);
    spriteUpdate();
  }

  //For the enemies
  public void update(Entity attacked){
    enemyAttackCollision(attacked);
    updateHitboxAttack();
    spriteUpdate();
  }

  public void spriteUpdate(){
    String direction = entity.direction;
    frameCounter++;
    int maxSprites = totalSprites-1;
    if(frameCounter>spriteThreshold){
      spriteCounter++;
      frameCounter=0;
      entity.gravity();
    }
    if(spriteCounter>maxSprites){
      endingAttack(direction,StateList.Attack_3.ordinal());
    }
    entity.changeDirection(direction);
  }

  public void inputHandler(String direction){
  }
}

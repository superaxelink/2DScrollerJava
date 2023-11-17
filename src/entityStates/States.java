package entityStates;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entity.Entity;
import entity.Entity.StateList;
import main.GamePanel;

public class States {

  GamePanel gp;
  Entity entity;

  //Main Image Characteristics
  public int spriteThreshold;
  public int totalSprites; 
  public int baseWidth; 
  public int baseHeight;

  public BufferedImage spriteSheet;
  public BufferedImage spriteSheetLeft;

  //SubImage characteristics
  public int width;
  public int height;
  public int dividedWidth;
  public int widthOffset;
  public int xStartSubimage;
  public int yStartSubimage;
  public int speed = 0;

  public BufferedImage subImage;

  public int stateNumber;

  //hitbox
  public int widthHitBox;
  public int offsetHitBoxRight;
  public int offsetHitBoxLeft;

  //Attack hitbox
  public int xAttackHitboOffset;
  public int yAttackHitboOffset;
  public int xStartSquare;
  public int xEndSquare;
  public int yStartSquare;
  public int yEndSquare;
  public int widthAttack;
  public int heightAttack;
  public int spriteContact;
  public int knockBackvx=0;
  
  public int attackDamage;

  //character characteristics
  public String type;

  //For on air states
  public int startingJump;
  public int goingUp;
  public int maxHeight;
  public int falling;
  public int landing;

  //counters
  public int spriteCounter = 0;
  public int stateCounter = 0;
  public int frameCounter = 0;


  public States(GamePanel gp, Entity entity, 
    int spriteThreshold, int totalSprites, int baseWidth, int baseHeight,
    int height, int yStartSubimage, int widthOffset, int widthHitBox, 
    int offsetHitBoxRight, int offsetHitBoxLeft, int stateNumber,
    String type, String pathRight, String pathLeft){
    this.gp = gp;
    this.entity = entity;
    this.type = type;
    this.stateNumber = stateNumber;

    //basic characteristics
    this.spriteThreshold = spriteThreshold;
    this.totalSprites = totalSprites;
    this.baseWidth = baseWidth;
    this.baseHeight = baseHeight;

    this.height = height;
    this.yStartSubimage = yStartSubimage;

    this.widthOffset = widthOffset;

    this.widthHitBox = widthHitBox;
    this.offsetHitBoxRight = offsetHitBoxRight;
    this.offsetHitBoxLeft = offsetHitBoxLeft;

    dividedWidth = this.baseWidth/this.totalSprites;
    width = dividedWidth;
    
    spriteSheet = gp.uTool.setup(pathRight, baseWidth, baseHeight);
    spriteSheetLeft = gp.uTool.setup(pathLeft, baseWidth, baseHeight);
    if(spriteSheetLeft==null){
      gp.uTool.mirrorImage(spriteSheet,"res/" + pathLeft + ".png");
      gp.uTool.mirrorImage(spriteSheet,"bin/" + pathLeft + ".png");
      spriteSheetLeft = gp.uTool.setup(pathLeft, baseWidth, baseHeight);
    }
  }

  public void randomDirection(String direction){
    double rand = Math.random();
    if(rand<0.02){
      switch(direction){
        case "left": entity.direction="right"; break;
        case "right": entity.direction="left"; break;
      }
    }
  }

  public void draw(Graphics2D g2, int x, int y){
    g2.drawImage(subImage, x, y, null);
    drawMonsterLife(g2, x, y);
  }

  public void drawMonsterLife(Graphics2D g2, int x, int y){
    if(!entity.type.equals("player") 
      && entity.currentLife<entity.baseLife
      && entity.currentState.stateNumber!=StateList.Dead.ordinal()){
      // Configura el color de la línea
      g2.setColor(Color.BLACK);
      // Dibuja una línea desde (50, 50) hasta (150, 150)
      g2.drawLine(x + width/2 - 25, y-10, x + width/2 + 25, y-10);
      // Configura el color de la línea
      if(entity.currentLife>=0){
        g2.setColor(Color.RED);
        // Dibuja una línea desde (50, 50) hasta (150, 150)
        g2.drawLine(x + width/2 - 25, y-10, x + width/2 - 25 + (50*entity.currentLife/entity.baseLife), y-10);
      }
    }
  }

  public void playerAttackCollision(ArrayList<Entity> enemies){
    if(spriteCounter>=spriteContact){
      for(Entity enemy : enemies){
        //Parry
        if(enemy.currentState.stateNumber==StateList.Attack_1.ordinal() ||
        enemy.currentState.stateNumber==StateList.Attack_2.ordinal() ||
        enemy.currentState.stateNumber==StateList.Attack_3.ordinal()){
          if(parryDetected(enemy)){
            enemy.changeState(StateList.Idle_1.ordinal(), enemy.direction);
          }
        }
        //Damage done by the player
        if(!enemy.invincible && enemy.currentState.stateNumber!=StateList.Dead.ordinal()){
          if(collisionDetected(enemy)){
            handleCollision(enemy);
            if(entity.direction.equals("left")){
              enemy.direction="right";
            }else if(entity.direction.equals("right")){
              enemy.direction="left";
            }
            if(enemy.stateList[StateList.Hurt.ordinal()]!=null){
              enemy.changeState(StateList.Hurt.ordinal(), enemy.direction);
            }
          }
        }
      }
    }
  }

  private boolean collisionDetected(Entity enemy) {
    return !enemy.invincible && gp.uTool.squareCollision(
        xStartSquare, xEndSquare, yStartSquare, yEndSquare,
        enemy.xStartSquare, enemy.xEndSquare, enemy.y, enemy.yEndSquare
    );
  }

  private boolean parryDetected(Entity enemy) {
    return gp.uTool.squareCollision(xStartSquare, xEndSquare, yStartSquare, yEndSquare, 
    enemy.currentState.xStartSquare, enemy.currentState.xEndSquare, enemy.currentState.yStartSquare, enemy.currentState.yEndSquare);
  }

  private void handleCollision(Entity enemy) {
    if (entity.direction.equals("right")) {
        enemy.knockBackvx = knockBackvx;
    } else if (entity.direction.equals("left")) {
        enemy.knockBackvx = -knockBackvx;
    }
    enemy.currentLife -= entity.currentState.attackDamage;
    enemy.invincible = true;
  }

  public void enemyAttackCollision(Entity attacked){
    //face the oponent to attack
    if(attacked.direction.equals("left")){
      entity.direction = "right";
    }else{
      entity.direction = "left";
    }
    //check collision between the enemy attack and hitbox of the player 
    //check collision between attack square and attacked character
    if(attacked.currentState.stateNumber!=StateList.Dead.ordinal() && collisionDetected(attacked)){
      if(entity.direction.equals("right")){
        attacked.knockBackvx=knockBackvx;
        attacked.direction = "left";
      }else if(entity.direction.equals("left")){
        attacked.knockBackvx=-knockBackvx;
        attacked.direction = "right";
      }
      attacked.currentLife -= entity.currentState.attackDamage;
      attacked.changeState(StateList.Hurt.ordinal(), attacked.direction);
    }
  }

  public void updateHitboxAttack(){
    if(entity.direction.equals("right")){
      xStartSquare = entity.xEndSquare + xAttackHitboOffset;
      xEndSquare = xStartSquare + widthAttack;
    }else if(entity.direction.equals("left")){
      xStartSquare = entity.xStartSquare-widthAttack;
      xEndSquare = xStartSquare + widthAttack;
    }
    yStartSquare = entity.y + yAttackHitboOffset;
    yEndSquare = yStartSquare + heightAttack;
  }

  public void endingAttack(String direction, int nextAttackState){
    int currentHeight = entity.onGround() ? entity.background.groundLine - entity.currentState.height : entity.y;
    if(gp.keyH.keys.contains("attack")){
      entity.changeState(nextAttackState,direction, currentHeight);
    }else{
      int stateToChange = StateList.Idle_1.ordinal();
      int falling = 0;
      if(!entity.onGround()){
        stateToChange =StateList.Jump.ordinal();
        falling = entity.stateList[StateList.Jump.ordinal()].falling ;
      }
      entity.changeState(stateToChange, direction, currentHeight, falling);
    }
    spriteCounter = 0;
  }

  public void checkPlayerCollisions(ArrayList<Entity> enemies){
    for(int i=0;i<enemies.size();i++){
      if(!entity.invincible){
        if(gp.uTool.squareCollision(entity, enemies.get(i))){
          entity.currentLife -= enemies.get(i).damage;
          entity.invincible = true;
          entity.changeState(StateList.Hurt.ordinal(), entity.direction);
        }
      }
    }
  }

  //NPC movement
  public void npcHorizontalMovement(){
    String direction = entity.direction;
    if(type.equals("enemy")){
      randomDirection(direction);
      if(direction.equals("left")){entity.x-=speed;}
      else if(direction.equals("right")){entity.x+=speed;}
    }
  }

  public void inputHandler(String direction){}

  //player update method
  public void update(ArrayList<Entity> enemies){
    if(type.equals("player") && entity.invincible){
      entity.invinicibleCounter++;
      if(entity.invinicibleCounter>entity.invincibleTime){
        entity.invincible = false;
        entity.invinicibleCounter=0;
      }
    }
    else if(type.equals("player") && !entity.invincible && entity.currentState.stateNumber!=StateList.Dead.ordinal()){
      checkPlayerCollisions(enemies);
    }

    spriteUpdate();
  }

  //npc update method
  public void update(Entity attacked){
    spriteUpdate();
    if(entity.type.equals("enemy") & entity.currentLife<=0){
      spriteCounter=0;
      stateCounter++;
      entity.changeState(StateList.Dead.ordinal(), entity.direction);
    }
  }

  public void spriteUpdate(){}
}
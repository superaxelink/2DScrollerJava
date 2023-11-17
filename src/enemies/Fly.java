package enemies;

import entity.Entity;
import entityStates.StateDead;
import entityStates.StateFly;
import main.GamePanel;
import surrounding.Background;

public class Fly extends Entity{

  public Fly(GamePanel gp, Background background, int reference){
    this.gp = gp;
    this.background = background;
    setDefaultValues(reference);
  }  

  public void setDefaultValues(int reference){
    worldX = gp.screenWidth/2;
    worldY = (int) Math.floor(background.groundLine*(1-Math.random()));
    weight = 13;
    direction = "left";
    type = "enemy";
    
    invincible = false;
    invincibleTime = 25;

    double rand = Math.random();
    if(rand <0.25){
      baseVx = 1;
      runVx = 2*baseVx;
      baseVy = 18;
      vx = baseVx;
      vy = baseVy;
      baseLife = 3;
      damage = 1;
      points = 20;
      initStates2("black");
    }else if( 0.25<=rand && rand <0.5){
      baseVx = 1;
      runVx = 2*baseVx;
      baseVy = 18;
      vx = baseVx;
      vy = baseVy;
      baseLife = 3;
      damage = 1;
      points = 30;
      initStates("brown");
    }else if(0.5<=rand && rand<0.8){
      baseVx = 2;
      runVx = 2*baseVx;
      baseVy = 24;
      vx = baseVx;
      vy = baseVy;
      baseLife = 3;
      damage = 2;
      points = 40;
      initStates("green");
    }else{
      baseVx = 3;
      runVx = 2*baseVx;
      baseVy = 30;
      vx = baseVx;
      vy = baseVy;
      baseLife = 3;
      damage = 3;
      initStates("horned");
    }

    currentLife = baseLife;
    currentState = stateList[StateList.Fly.ordinal()];
    currentState.spriteCounter=0;
    changeDirection(direction);

    groundLine =  worldY-currentState.height;

    rand = Math.random();
    if(rand>0.5){
      x = reference + worldX + currentState.width/2;
    }else{
      x = reference - worldX - currentState.width/2;
      direction="right";
    }
    y = worldY - currentState.height;
  }

  //@Override
  public void update(Entity attacked){
    if(vx!=0){
      knockBack();
    }
    checkDirection();
    verticalMovent();
    updateHitbox();
    currentState.inputHandler(direction);
    currentState.update(attacked);
  }
  
  public void checkDirection(){}
  
  public void verticalMovent(){
    angle += 0.02;
    int rand = (int) (20*Math.random()-10); 
    y += 3*Math.sin(angle) + rand;
  }
  
  public void initStates(String color){
    stateList[StateList.Fly.ordinal()] = new StateFly(gp, this, 4, 
    2,120, 50, 50, 0, 0, 
    45, 22, 22, StateList.Fly.ordinal(), 3,
    type, "/enemy/" + color + "Fly/spritesheet", "/enemy/" + color + "Fly/spritesheetLeft");

    stateList[StateList.Dead.ordinal()] = new StateDead(gp, this, 6,
    5, 500, 90, 90, 0, 0, 
    31, 14, StateList.Dead.ordinal(),
    type, "/enemy/boom", "/enemy/boom");
  }
  
  public void initStates2(String color){
    stateList[StateList.Fly.ordinal()] = new StateFly(gp, this, 4, 
    6,360, 44, 44, 0, 0, 
    45, 22, 22, StateList.Fly.ordinal(), 3,
    type, "/enemy/" + color + "Fly/spritesheet", "/enemy/" + color + "Fly/spritesheetLeft");

    stateList[StateList.Dead.ordinal()] = new StateDead(gp, this, 6,
    5, 500, 90, 90, 0, 0, 
    31, 14, StateList.Dead.ordinal(),
    type, "/enemy/boom", "/enemy/boom");
  }
}

package enemies;

import entity.Entity;
import entityStates.StateDead;
import entityStates.StateIdle1;
import main.GamePanel;
import surrounding.Background;

public class Plant extends Entity{
  
  public Plant(GamePanel gp, Background background, int reference){
    this.gp = gp;
    this.background = background;
    setDefaultValues(reference);
  }

  public void setDefaultValues(int reference){
    worldX = gp.screenWidth/2;
    worldY = background.groundLine;
    weight = 13;
    direction = "left";
    type = "enemy";

    invincible = false;
    invincibleTime = 25;

    baseVx = 1;
    runVx = 2*baseVx;
    baseVy = 18;
    vx = baseVx;
    vy = baseVy;
    baseLife = 3;
    currentLife = baseLife;
    damage = 1;
    points = 10;
    initStates("black");

    currentState = stateList[StateList.Idle_1.ordinal()];
    currentState.spriteCounter=0;
    changeDirection(direction);

    groundLine =  worldY-currentState.height;

    double rand = Math.random();
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
    updateHitbox();
    currentState.inputHandler(direction);
    currentState.update(attacked);
  }

  public void checkDirection(){}

  public void initStates(String color){
    stateList[StateList.Idle_1.ordinal()] = new StateIdle1(gp, this, 4, 
    2,120, 87, 87, 0, 0, 
    50, 25, 25, StateList.Idle_1.ordinal(),
    type, "/enemy/plant/Plant", "/enemy/plant/PlantLeft");

    stateList[StateList.Dead.ordinal()] = new StateDead(gp, this, 6,
    5, 500, 90, 90, 0, 0, 
    31, 14, StateList.Dead.ordinal(),
    type, "/enemy/boom", "/enemy/boom");
  }
}

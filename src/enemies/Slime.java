package enemies;

import entity.Entity;
import entityStates.StateAttack1;
import entityStates.StateAttack2;
import entityStates.StateAttack3;
import entityStates.StateDead;
import entityStates.StateHurt;
import entityStates.StateIdle1;
import entityStates.StateJump;
import entityStates.StateRun;
import entityStates.StateWalk;
import main.GamePanel;
import surrounding.Background;

public class Slime extends Entity{

  public Slime(GamePanel gp, Background background, int reference){
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

    double rand = Math.random();
    if(rand <0.5){
      baseVx = 1;
      runVx = 2*baseVx;
      baseVy = 18;
      vx = baseVx;
      vy = baseVy;
      baseLife = 3;
      damage = 1;
      points = 10;
      initStates("green");
    }else if(0.5<=rand && rand<0.8){
      baseVx = 2;
      runVx = 2*baseVx;
      baseVy = 24;
      baseLife = 6;
      vx = baseVx;
      vy = baseVy;
      damage = 1;
      points = 20;
      initStates("blue");
    }else{
      baseVx = 3;
      runVx = 2*baseVx;
      baseVy = 30;
      baseLife = 9;
      vx = baseVx;
      vy = baseVy;
      damage = 2;
      points = 30;
      initStates("red");
    }

    currentLife = baseLife;
    currentState = stateList[StateList.Idle_1.ordinal()];
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
    updateHitbox();
    currentState.inputHandler(direction);
    currentState.update(attacked);
  }

  public void checkDirection(){}

  public void horizontalMovent(){}
  
  public void initStates(String color){
    stateList[StateList.Idle_1.ordinal()] = new StateIdle1(gp, this, 4, 
    8,1024, 128, 33, 95, 0, 
    42, 23, 20, StateList.Idle_1.ordinal(),
    type, "/enemy/" +color+"Slime/Idle", "/enemy/" +color+"Slime/IdleLeft");

    stateList[StateList.Walk.ordinal()] = new StateWalk(gp, this, 4, 
    8,1024, 128, 33, 95, 0, baseVx,
    42, 23, 18, StateList.Walk.ordinal(), 
    type, "/enemy/" +color+"Slime/Walk", "/enemy/" +color+"Slime/WalkLeft");

    stateList[StateList.Run.ordinal()] = new StateRun(gp, this, 4, 
    7,896, 128, 33, 95, 0, runVx,
    42, 23, 10, StateList.Run.ordinal(),
    type, "/enemy/" +color+"Slime/Run", "/enemy/" +color+"Slime/RunLeft");

    stateList[StateList.Jump.ordinal()] = new StateJump(gp, this, 5,
    13, 1664, 128, 62, 66, 0, 
    2, 4, 7, 8, 11, 
    34, 8, 25, StateList.Jump.ordinal(),
    baseVx,
    type, "/enemy/" +color+"Slime/Jump","/enemy/" +color+"Slime/Jump");

    stateList[StateList.Attack_1.ordinal()] = new StateAttack1(gp, this, 6,
    4, 512, 128, 33, 95, 0, 
    35, 28, 10,
    30, 30, 0, 0,
    3, StateList.Attack_1.ordinal(), 1,
    type, "/enemy/" +color+"Slime/Attack_1", "/enemy/" +color+"Slime/AttackLeft_1");

    stateList[StateList.Attack_2.ordinal()] = new StateAttack2(gp, this, 6,
    4, 512, 128, 33, 95, 0, 
    33, 24, 8,
    30, 20, 0, 0,
    3, StateList.Attack_2.ordinal(), 2,
    type, "/enemy/" +color+"Slime/Attack_2","/enemy/" +color+"Slime/AttackLeft_2");

    stateList[StateList.Attack_3.ordinal()] = new StateAttack3(gp, this, 6,
    5, 640, 128, 33, 95, 0,
    35, 20, 14,
    22, 30, 0, 0,
    2, StateList.Attack_3.ordinal(), 3, 
    type, "/enemy/" +color+"Slime/Attack_3","/enemy/" +color+"Slime/AttackLeft_3");

    stateList[StateList.Hurt.ordinal()] = new StateHurt(gp, this, 6,
    6, 768, 128, 73, 55, 0, 
    31, 14, StateList.Hurt.ordinal(),
    type, "/enemy/" +color+"Slime/Hurt", "/enemy/" +color+"Slime/HurtLeft");

    stateList[StateList.Dead.ordinal()] = new StateDead(gp, this, 6,
    5, 500, 90, 90, 0, 0, 
    31, 14, StateList.Dead.ordinal(),
    type, "/enemy/boom", "/enemy/boom");
/*     gp.uTool.mirrorImage(stateList[StateList.Idle_1.ordinal()].spriteSheet, 
    "/home/axl/Documents/java/Games/testGame/res/enemy/greenSlime/IdleLeft.png"); */
  }
}

package entity;

import java.util.ArrayList;

import entityStates.StateAttack1;
import entityStates.StateAttack2;
import entityStates.StateAttack3;
import entityStates.StateDead;
import entityStates.StateHurt;
import entityStates.StateIdle1;
import entityStates.StateIdle2;
import entityStates.StateJump;
import entityStates.StateRun;
import entityStates.StateWalk;
import main.GamePanel;
import main.KeyHandler;
import surrounding.Background;

public class Player extends Entity{

  public Player(GamePanel gp, KeyHandler keyHandler, Background background){
    this.gp = gp;
    this.keyHandler = keyHandler;
    this.background = background;
    setDefaultValues();
  }

  public void setDefaultValues(){
    worldX = gp.screenWidth/2;
    worldY = background.groundLine;
    baseVx = 4;
    runVx = 2*baseVx;
    baseVy = 27;
    vx = 0;
    baseLife = 10;
    currentLife = baseLife;
    vy = baseVy;
    weight = 10;
    direction = "right";
    type = "player";
    invincible = false;
    invincibleTime = 50;
    totalPoints = 0;

    initStates();

    heart1 = gp.uTool.setup("/player/heart_blank", 50, 50);
    heart2 = gp.uTool.setup("/player/heart_full", 50, 50);
    heart3 = gp.uTool.setup("/player/heart_half", 50, 50);

    currentState = stateList[StateList.Idle_1.ordinal()];
    currentState.spriteCounter=0;
    changeDirection(direction);
    
    groundLine =  background.groundLine-currentState.height;
    x = worldX - currentState.width/2;
    y = worldY - currentState.height;
  }

  //@Override
  public void update(ArrayList<Entity> enemies){
    if(vx!=0){
      knockBack();
    }
    checkDirection();
    updateHitbox();
    currentState.inputHandler(direction);
    currentState.update(enemies);
    horizontalMovent(enemies);
  }

  public void gravity(){
    super.gravity();
    
    if(keyHandler.downPressed){
      if(y<background.groundLine - currentState.height){
        y += weight*2;
      }
    }
  }

  public void checkDirection(){
    if(keyHandler.leftPressed && currentState.stateNumber!=StateList.Dead.ordinal()){
      direction = "left";
    }
    if(keyHandler.rightPressed && currentState.stateNumber!=StateList.Dead.ordinal()){
      direction = "right";
    }
  }

  public void reset(){
    currentLife = baseLife;
    direction = "right";
    type = "player";
    invincible = false;
    totalPoints = 0;

    currentState = stateList[StateList.Idle_1.ordinal()];
    currentState.spriteCounter=0;
    changeDirection(direction);
    
    groundLine =  background.groundLine-currentState.height;
    x = worldX - currentState.width/2;
    y = worldY - currentState.height;
  }

  public void initStates(){
    stateList[StateList.Idle_1.ordinal()] = new StateIdle1(gp, this, 4, 
    8,1024, 128, 73, 55, 0, 
    31, 12, 16, StateList.Idle_1.ordinal(),
    type, "/player/ChibiMale/Swordsman/Idle", "/player/ChibiMale/Swordsman/IdleLeft");

    stateList[StateList.Idle_2.ordinal()] = new StateIdle2(gp, this, 6,
      3, 384, 128, 73, 55, 0, 
      31, 14, 18, StateList.Idle_2.ordinal(),
      type, "/player/ChibiMale/Swordsman/Idle_2", "/player/ChibiMale/Swordsman/IdleLeft_2");

    stateList[StateList.Walk.ordinal()] = new StateWalk(gp, this, 5,
    8, 1024, 128, 76, 52, 0, baseVx, 
    28, 20, 8, StateList.Walk.ordinal(),
    type, "/player/ChibiMale/Swordsman/Walk","/player/ChibiMale/Swordsman/WalkLeft");

    stateList[StateList.Jump.ordinal()] = new StateJump(gp, this, 5,
    8, 1024, 128, 73, 55, 0, 
    2, 3, 4, 5, 6, 
    34, 8, 25, StateList.Jump.ordinal(),
    baseVx,
    type, "/player/ChibiMale/Swordsman/Jump","/player/ChibiMale/Swordsman/JumpLeft");

    stateList[StateList.Run.ordinal()] = new StateRun(gp, this, 5,
    8, 1024, 128, 73, 55, 0, runVx,
    35, 13, 21, StateList.Run.ordinal(),
    type, "/player/ChibiMale/Swordsman/Run", "/player/ChibiMale/Swordsman/RunLeft");
    
    stateList[StateList.Attack_1.ordinal()] = new StateAttack1(gp, this, 6,
    6, 768, 128, 75, 53, 0, 
    32, 22, 10,
    60, 40, 0, 20,
    4, StateList.Attack_1.ordinal(), 1,
    type, "/player/ChibiMale/Swordsman/Attack_1", "/player/ChibiMale/Swordsman/AttackLeft_1");
    
    stateList[StateList.Attack_2.ordinal()] = new StateAttack2(gp, this, 12,
    3, 384, 128, 75, 53, 0, 
    33, 14, 20,
    45, 40, 0, 20,
    1, StateList.Attack_2.ordinal(), 2,
    type, "/player/ChibiMale/Swordsman/Attack_2","/player/ChibiMale/Swordsman/AttackLeft_2");
    
    stateList[StateList.Attack_3.ordinal()] = new StateAttack3(gp, this, 8,
    4, 512, 128, 75, 53, 0,
    31, 14, 18,
    45, 52, 0, 20,
    1, StateList.Attack_3.ordinal(), 3,
    type, "/player/ChibiMale/Swordsman/Attack_3","/player/ChibiMale/Swordsman/AttackLeft_3");
    
    stateList[StateList.Hurt.ordinal()] = new StateHurt(gp, this, 8,
    3, 384, 128, 73, 55, 0, 
    31, 14, StateList.Hurt.ordinal(),
    type, "/player/ChibiMale/Swordsman/Hurt", "/player/ChibiMale/Swordsman/HurtLeft");
    
    stateList[StateList.Dead.ordinal()] = new StateDead(gp, this, 20,
    3, 384, 128, 73, 55, 0, 
    31, 14, StateList.Dead.ordinal(),
    type, "/player/ChibiMale/Swordsman/Dead", "/player/ChibiMale/Swordsman/DeadLeft");
  }

  /*       if(t<360){
        t+=1;
      }else{
        t=0;
      }
      posX +=(int)Math.round(10*Math.cos(Math.PI*t/180));
      posY +=(int)Math.round(10*Math.sin(Math.PI*t/180)); */
}

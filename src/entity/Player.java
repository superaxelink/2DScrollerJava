package entity;

import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import playerStates.StateAttack1;
import playerStates.StateAttack2;
import playerStates.StateAttack3;
import playerStates.StateDead;
import playerStates.StateHurt;
import playerStates.StateIdle1;
import playerStates.StateIdle2;
import playerStates.StateJump;
import playerStates.StateRun;
import playerStates.StateWalk;
import playerStates.States;
import surrounding.Background;

public class Player extends Entity{
  
  public KeyHandler keyHandler;

  public int groundLine;

  //Player states
  public enum PlayerState{
    Attack_1,
    Attack_2,
    Attack_3,
    Dead,
    Hurt,
    Idle_1,
    Idle_2,
    Jump,
    Run,
    Walk
  }

  public int state;

  public States stateList[] = new States[10];
  public States currentState;

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
    baseVy = 27;
    vx = baseVx;
    vy = baseVy;
    jumpHeight = 60;
    weight = 10;
    direction = "right";

    initStates();

    currentState = stateList[PlayerState.Idle_1.ordinal()];
    currentState.spriteCounter=0;
    currentState.changeDirection(direction);
    
    groundLine =  background.groundLine-currentState.height;
    x = worldX - currentState.width/2;
    y = worldY - currentState.height;
  }

  public void update(){
    checkDirection();
    currentState.inputHandler(direction);
    currentState.update();
  }

  public void gravity(){
    if(y<groundLine){
      y+=weight;
    }
    else if(y>groundLine){
      y=groundLine;
    }

    if(keyHandler.downPressed){
      if(y<background.groundLine - currentState.height){
        y += weight*2;
      }
    }
  }

  public void checkDirection(){
    if(keyHandler.leftPressed){
      direction = "left";
    }
    if(keyHandler.rightPressed){
      direction = "right";
    }
  }

  public void horizontalMovent(int vx){
    if(keyHandler.leftPressed){
      x -= vx;
    }
    if(keyHandler.rightPressed){
      x += vx;
    }
  }

  public boolean onGround(){//checks if player is on ground
    return y>=groundLine;
  }

  public void draw(Graphics2D g2){
    image = currentState.subImage;
    g2.drawImage(image, x, y, null);
  }

  public void initStates(){
    stateList[PlayerState.Idle_1.ordinal()] = new StateIdle1(gp, this);
    stateList[PlayerState.Idle_2.ordinal()] = new StateIdle2(gp, this);
    stateList[PlayerState.Walk.ordinal()] = new StateWalk(gp, this);
    stateList[PlayerState.Jump.ordinal()] = new StateJump(gp, this);
    stateList[PlayerState.Run.ordinal()] = new StateRun(gp, this);
    stateList[PlayerState.Attack_1.ordinal()] = new StateAttack1(gp, this);
    stateList[PlayerState.Attack_2.ordinal()] = new StateAttack2(gp, this);
    stateList[PlayerState.Attack_3.ordinal()] = new StateAttack3(gp, this);
    stateList[PlayerState.Hurt.ordinal()] = new StateHurt(gp, this);
    stateList[PlayerState.Dead.ordinal()] = new StateDead(gp, this);
  }

  /*       if(t<360){
        t+=1;
      }else{
        t=0;
      }
      posX +=(int)Math.round(10*Math.cos(Math.PI*t/180));
      posY +=(int)Math.round(10*Math.sin(Math.PI*t/180)); */
}

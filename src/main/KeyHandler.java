package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener{
  
  GamePanel gp;
  public ArrayList<String> keys = new ArrayList<>();
  public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, attackPressed, enterPressed;
  
  public KeyHandler(GamePanel gp){
    this.gp = gp;
  }

  @Override
  public void keyPressed(KeyEvent e){
    int code =e.getKeyCode();
    if(code==KeyEvent.VK_W){
      if(!keys.contains("up")){
        keys.add("up");
      }
      upPressed = true;
    }
    if(code==KeyEvent.VK_A){
      if(!keys.contains("left")){
        keys.add("left");
      }
      leftPressed = true;
    }
    if(code==KeyEvent.VK_S){
      if(!keys.contains("down")){
        keys.add("down");
      }
      downPressed = true;
    }
    if(code==KeyEvent.VK_D){
      if(!keys.contains("right")){
        keys.add("right");
      }
      rightPressed = true;
    }
    if(code==KeyEvent.VK_SPACE){
      if(!keys.contains("space")){
        keys.add("space");
      }
      spacePressed = true;
    }
    if(code==KeyEvent.VK_ENTER){
      if(!keys.contains("enter")){
        keys.add("enter");
      }
      enterPressed = true;
    }
    if(code==KeyEvent.VK_J){
      if(!keys.contains("attack")){
        keys.add("attack");
      }
      attackPressed = true;
    }
    if(code==KeyEvent.VK_H){
      gp.drawHitBox = !gp.drawHitBox;
    }
  }

  @Override
  public void keyReleased(KeyEvent e){
    int code = e.getKeyCode();
    if(code==KeyEvent.VK_W){
      if(keys.contains("up")){
        keys.remove("up");
      }
      upPressed = false;
    }
    if(code==KeyEvent.VK_A){
      if(keys.contains("left")){
        keys.remove("left");
      }
      leftPressed = false;
    }
    if(code==KeyEvent.VK_S){
      if(keys.contains("down")){
        keys.remove("down");
      }
      downPressed = false;
    }
    if(code==KeyEvent.VK_D){
      if(keys.contains("right")){
        keys.remove("right");
      }
      rightPressed = false;
    }
    if(code==KeyEvent.VK_SPACE){
      if(keys.contains("space")){
        keys.remove("space");
      }
      spacePressed = false;
    }
    if(code==KeyEvent.VK_ENTER){
      if(keys.contains("enter")){
        keys.remove("enter");
      }
      enterPressed = false;
    }
    if(code==KeyEvent.VK_J){
      if(keys.contains("attack")){
        keys.remove("attack");
      }
      attackPressed = false;
    }
  }

  @Override
  public void keyTyped(KeyEvent e){

  }
}

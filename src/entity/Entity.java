package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import entityStates.States;
import main.GamePanel;
import main.KeyHandler;
import surrounding.Background;


public class Entity {
  
  //Entity system characteristics
  public BufferedImage spriteSheet;
  public BufferedImage image;
  public BufferedImage heart1, heart2, heart3;

  public Rectangle solidArea;

  public GamePanel gp;
  public Background background;

  //Entity playable characteristics
  public int worldX, worldY;
  public int x;
  public int y;
  public int baseVx;
  public int runVx;
  public int baseVy;
  public int vx;
  public int vy;
  public int weight;
  public String direction;
  public String type;

  public int baseLife;
  public int currentLife;

  public int groundLine;

  public int totalPoints;
  public int points;

  public double angle = 0;

  //COUNTERS
  public int frameCounter=0;

  //Available states for the entity
  public States stateList[] = new States[11];
  public States currentState;

  //HITBOX
  public int xStartSquare;
  public int xEndSquare;
  public int yEndSquare;

  //Invincibility
  public int invincibleTime;
  public int invinicibleCounter;
  public boolean invincible;

  //Attack properties
  public int damage;
  public int knockBackvx=0;

  //Player-only properties
  public KeyHandler keyHandler;

  //Player states
  public enum StateList{
    Attack_1,
    Attack_2,
    Attack_3,
    Dead,
    Hurt,
    Idle_1,
    Idle_2,
    Jump,
    Run,
    Walk,
    Fly
  }

  public void draw(Graphics2D g2, Color color, boolean drawHitBox){
    if(type.equals("player")){
      drawPunctuation(g2);
      for(int i=0; i<baseLife/2;i++){
        g2.drawImage(heart1, 50*i + 10, 1, null);
      }
      for(int i=0; i<currentLife;i++){
        if(i%2==0){
          g2.drawImage(heart3, 50*(i/2) + 10, 1, null);
        }else{
          g2.drawImage(heart2, 50*(i/2) + 10, 1, null);
        }
      }
    }
    currentState.draw(g2, x, y);
    if(drawHitBox){
      drawHitBox(g2, color, xStartSquare, y, currentState.widthHitBox, currentState.height);
    }
  }

  private void drawPunctuation(Graphics2D g2){
    // Cambiar el color del texto
    g2.setColor(Color.WHITE); // Puedes elegir cualquier color que desees

    // Cambiar el tamaño del texto
    Font originalFont = g2.getFont();
    Font newFont = originalFont.deriveFont(Font.BOLD, 20); // Puedes ajustar el tamaño según tus necesidades
    g2.setFont(newFont);
    String scoreString  = "Your score is: " + totalPoints;
    
    g2.drawString(scoreString, gp.screenWidth - g2.getFontMetrics().stringWidth(scoreString) - 20, 30);
  }

  public void update(ArrayList<Entity> enemies){} //to update the player
  public void update(Entity attacked){} //to update each enemy

  public void updateHitbox(){
    if(direction.equals("right")){
      xStartSquare = x+currentState.width/2-currentState.offsetHitBoxRight;
    }else if(direction.equals("left")){
      xStartSquare = x+currentState.width/2-currentState.offsetHitBoxLeft;
    }
    xEndSquare = xStartSquare + currentState.widthHitBox;
    yEndSquare = y + currentState.height;

    if(invincible){
      invinicibleCounter++;
      if(invinicibleCounter>invincibleTime){
        invincible = false;
        invinicibleCounter=0;
      }
    }
  }

  public void changeState(int newState, String direction, int... optParams){
    currentState = stateList[newState];
    int verticalPosition = (optParams.length>0) ? optParams[0] : background.groundLine-currentState.height;
    int currentSprite = (optParams.length>1) ? optParams[1] : 0;
    currentState.spriteCounter = currentSprite; 
    currentState.xStartSubimage = 0;
    changeDirection(direction);
    y = verticalPosition;
  }

  public void changeDirection(String direction){
    if(direction.equals("right") ){
      currentState.xStartSubimage = currentState.width*currentState.spriteCounter + currentState.widthOffset;
      currentState.subImage = currentState.spriteSheet.getSubimage(currentState.xStartSubimage, currentState.yStartSubimage, currentState.width, currentState.height);
    }else if(direction.equals("left") ){
      currentState.xStartSubimage = currentState.baseWidth - currentState.width*(currentState.spriteCounter+1) + currentState.widthOffset;
      currentState.subImage = currentState.spriteSheetLeft.getSubimage(currentState.xStartSubimage, currentState.yStartSubimage, currentState.width, currentState.height);
    }
  }

  public void gravity(){
    if(y<background.groundLine-currentState.height){
    //if(y<groundLine){
      y+=weight;
    }
    else if(y>background.groundLine-currentState.height){
    //else if(y>groundLine){
      //y=groundLine;
      y=background.groundLine-currentState.height;
    }
  }

  public void horizontalMovent(ArrayList<Entity> enemies){
    if(keyHandler.leftPressed && direction.equals("left")){
      vx = -currentState.speed;
    }else if(keyHandler.rightPressed && direction.equals("right")){
      vx = currentState.speed;
    }
    else{
      vx = 0;
    }
    vx+=knockBackvx;
    Iterator<Entity> it = enemies.iterator();
    while(it.hasNext()){
      Entity enemy =  it.next();
      enemy.x -= vx;
      if(enemy.x>x+gp.screenWidth/2 + enemy.currentState.width){
        it.remove();
      }
      if(enemy.x<x-gp.screenWidth/2-enemy.currentState.width){
        it.remove();
      }
      if(enemy.currentState.stateNumber==StateList.Dead.ordinal() 
      && enemy.currentState.spriteCounter>=enemy.currentState.totalSprites-1){
        totalPoints += enemy.points;
        it.remove();
      }
    }
    background.x +=vx;
  }

  public void knockBack(){
    if(knockBackvx>0){knockBackvx--;}
    else if(knockBackvx<0){knockBackvx++;}
    if(!type.equals("player")){
      x+=knockBackvx;
    }
  }

  public void drawHitBox(Graphics2D g2, Color color, int xs, int ys, int w, int h){
    AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    g2.setComposite(alphaComposite);
    g2.setColor(color);
    g2.fillRect(xs, ys, w , h);
    g2.setComposite(AlphaComposite.SrcOver);
  }

  public boolean onGround(){//checks if player is on ground
    //r eturn y>=groundLine;
    return y>=background.groundLine-currentState.height;
  }
}
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class menuHandler {
  
  GamePanel gp;
  public int width;
  public int height;

  public Font font;
  // Obtiene las dimensiones del texto
  public FontMetrics metrics;

  public int textWidth;
  public int textHeight;

  public int options;
  public int menuState;

  public int frameCounter;
  public int frameThreshold;

  public int x;
  public int y;

  public KeyHandler keyH;

  public BufferedImage image1;

  public String[] titles;
  public String[] mainMenuOptions;
  public String[] subMenuOptions;
  public String[] gameOverOptions;

  public int totalOptions;

  public menuHandler(GamePanel gp, KeyHandler keyH, int width, int height){
    this.gp = gp;
    this.width = width;
    this.height = height;
    this.keyH = keyH;

    this.font = new Font("Arial", Font.BOLD | Font.ITALIC, 24);

    options = 0;

    titles = new String[] {"MONSTER FIGHTER", "Choose your character", "GAME OVER! Is the best you can do?", "WINNER! Aren't you great?"};
    mainMenuOptions = new String[] {"Start game", "Exit game"};
    subMenuOptions = new String[] { "Swordsman", "Archer(not implemented)", "Wizard(not implemented)", "Go Back"};
    gameOverOptions = new String[] { "Retry", "To main screen", "Quit"};

    totalOptions = mainMenuOptions.length;

    image1 = gp.uTool.setup("/background/layerCity1", gp.screenWidth, gp.screenHeight);

    frameThreshold = 10;
    frameCounter=0;

    menuState = 0;
  }

  public void update(boolean gameStarted){
    if(frameCounter<frameThreshold){
      frameCounter+=1;
      return;
    }
    if(keyH.upPressed){
      options-=1;
    }
    else if(keyH.downPressed){
      options+=1;
    }
    if(options<1 || options>totalOptions){
      options=1;
    }
    if(!gameStarted){
      updateMainMenu();
    }else{
      updateGameOverMenu();
    }
    frameCounter=0;
  }

  public void updateGameOverMenu(){
    if(keyH.enterPressed){
      if(options==1){
        gp.resetGame();
      }
      if(options==2){
        options=0;
        menuState=0;
        gp.gameStarted=false;
        gp.resetGame();
      }
      if(options==3){
        gp.stop();
      }
    }
  }

  public void updateMainMenu(){
    if(menuState==0){
      if(keyH.enterPressed){
        if(options==1){
          menuState+=1;
        }
        if(options==2){
          gp.stop();
        }
      }
    }
    else if(menuState==1){
      if(keyH.keys.contains("enter")){
        if(options==1 || options==2 || options == 3){
          gp.gameStarted=true;
        }
        if(options==totalOptions){
          menuState = 0;
        }
      }
    }
  }

  public void drawTitle(Graphics2D g2, String text, int y){
    // Obtiene las dimensiones del texto
    if(metrics == null){
      metrics = g2.getFontMetrics(font);
    }
    textWidth = metrics.stringWidth(text);
    //int textHeight = metrics.getHeight();

    // Calcula la posición para centrar el texto en la pantalla
    x = (width - textWidth)/2;
    //int y = (height - textHeight) / 2 + metrics.getAscent();

    g2.setFont(font);
    g2.setColor(Color.black);
    g2.drawString(text, x, y);
  }

  public void drawOptions(Graphics2D g2, String text, int y){

    textWidth = metrics.stringWidth(text);
    //int textHeight = metrics.getHeight();

    // Calcula la posición para centrar el texto en la pantalla
    x = (width - textWidth)/2;
    //int y = (height - textHeight) / 2 + metrics.getAscent();

    g2.setFont(font);
    g2.setColor(Color.black);
    g2.drawString(text, x, y);
  }

  public void drawMenu(Graphics2D g2, String menuOptions[]){
    totalOptions = menuOptions.length;
    for (int i = 0; i < menuOptions.length; i++) {
      String prefix = (i + 1 == options) ? ">" : ""; // Agrega ">" solo a la opción seleccionada
      drawOptions(g2, prefix + menuOptions[i], height / 2 + 20 + i * 40);
    }
  }

  public void draw(Graphics2D g2){
/*     g2.setColor(Color.blue);
    g2.fillRect(0, 0, width, height); */

    g2.drawImage(image1, 0, 0, gp.screenWidth, gp.screenHeight, 
    0 , 0, gp.screenWidth, gp.screenHeight, null);
    if(menuState==0){
      drawTitle(g2, titles[0],50);
      drawMenu(g2, mainMenuOptions);
    }else if(menuState==1){
      drawTitle(g2, titles[1],50);
      drawMenu(g2, subMenuOptions);
    }
  }

  public void drawGameOverMenu(Graphics2D g2){
    
    drawTitle(g2, titles[2] ,100);
    
    drawMenu(g2,gameOverOptions);
  }

  public void drawWinnerMenu(Graphics2D g2){
    
    drawTitle(g2, titles[3] ,100);
    
    drawMenu(g2,gameOverOptions);
  }
}

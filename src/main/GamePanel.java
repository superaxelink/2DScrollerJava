package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import surrounding.Background;

public class GamePanel extends JPanel implements Runnable{

  // SYSTEM
  Thread gameThread;
  Graphics2D g2;
  public UtilityTool uTool = new UtilityTool();
  public KeyHandler keyH = new KeyHandler();

  // Screen settings
  public int screenWidth=700;
  public int screenHeight=500;

  //FPS
  int FPS = 60;

  //counter
  public int spritehreshold = 1;
  int counter = 0;
  float t=0;

  // ENTITIES, PLAYERS AND UPDATES
  Background background = new Background(this);
  Player player = new Player(this, keyH, background);

  public final int second = 1000000000;

  double[] xArray =  new double[100];

  public GamePanel(){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(keyH);
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g2 = (Graphics2D)g;  

    //Background
    background.draw(g2);

    //Player
    player.draw(g2);
    //g2.setColor(Color.red);
    //g2.fillRect(player.posX, player.posY, player.size, player.size);

    g2.dispose();
  }

  public void update(){
    player.update();
  }

  @Override
  public void run() {
    double drawInterval = second/FPS;
    //double nextDrawTime = System.nanoTime() + drawInterval;
    double delta = 0;//time control
    long lastTime = System.nanoTime();
    long currentTime;

    while(gameThread!=null){
      
      currentTime = System.nanoTime();

      delta += (currentTime - lastTime)/drawInterval;
      lastTime = currentTime;

      if(delta>=spritehreshold){
        update();
        repaint();
        delta--;
      }
    }
  }
  
  public void startGameThread(){
    
    gameThread = new Thread(this);
    gameThread.start();
  }
}

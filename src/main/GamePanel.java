package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import entity.Entity;
import enemies.Fly;
import enemies.Plant;
import enemies.Slime;
import entity.Player;
import entity.Entity.StateList;
import surrounding.Background;

public class GamePanel extends JPanel implements Runnable{

  // SYSTEM
  public JFrame window;
  Thread gameThread;
  Graphics2D g2;
  private volatile boolean running = true;
  public UtilityTool uTool = new UtilityTool();
  public KeyHandler keyH = new KeyHandler(this);

  //gameGoing
  public boolean gameStarted = true;
  public boolean gameOver = false;

  // Screen settings
  public int screenWidth=700;
  public int screenHeight=500;

  //Hitbox
  public boolean drawHitBox = false;

  //FPS
  int FPS = 60;

  //counter
  public int spritehreshold = 1;
  int counter = 0;
  float t=0;

  menuHandler menu = new menuHandler(this, keyH, screenWidth, screenHeight);

  // ENTITIES, PLAYERS AND UPDATES
  Background background = new Background(this);
  Player player = new Player(this, keyH, background);
  ArrayList<Entity> enemies = new ArrayList<Entity>();

  public final int second = 1000000000;
  public final int pointsToWin =200;

  double[] xArray =  new double[100];

  public GamePanel(JFrame window){
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));
    this.setBackground(Color.black);
    this.setFocusable(true);
    this.addKeyListener(keyH);
    this.window = window;
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    g2 = (Graphics2D)g;  

    if(gameStarted){
      //Background
      background.draw(g2);

      //testEnemy.draw(g2);
      //Enemies
      if(!enemies.isEmpty()){
        for(int i=0; i<enemies.size();i++){
          enemies.get(i).draw(g2, Color.RED, drawHitBox);
        }
      }

      //Player
      player.draw(g2, Color.BLUE, drawHitBox);

      if(player.totalPoints>=pointsToWin){
        menu.drawWinnerMenu(g2);
      }
      if(player.currentState.stateNumber==StateList.Dead.ordinal()){
        menu.drawGameOverMenu(g2);
      }
      if(player.currentLife>0 &&  player.totalPoints<=0){
        menu.drawTitle(g2, "Beat the monsters!", 70);
        menu.drawTitle(g2, "Get " + pointsToWin + " points!", 120);
      }
    }else{
      menu.draw(g2);
    }

    g2.dispose();
  }

  public void resetGame(){
    background.reset();
    enemies.clear();
    player.reset();
  }

  public void update(){
    if(gameStarted && player.totalPoints<pointsToWin){
      background.update();
      player.update(enemies);
      //Add enemies
      double rand = Math.random();
      if(rand<0.01 & enemies.size()<10){
        enemies.add(new Slime(this, background, player.x));
      }
      else if(0.51<rand & rand<0.52 & enemies.size()<10){
        enemies.add(new Fly(this, background, player.x));
      }
      else if(0.91<rand & rand<0.92 & enemies.size()<10){
        enemies.add(new Plant(this, background, player.x));
      }
      //Update enemies
      if(!enemies.isEmpty()){
        for(int i=0; i<enemies.size();i++){
          enemies.get(i).update(player);
        }
      }
      if(player.currentState.stateNumber==StateList.Dead.ordinal()){
        menu.update(gameStarted);;
      }
    }else{
      menu.update(gameStarted);
    }
  }

  @Override
  public void run() {
    double drawInterval = second/FPS;
    //double nextDrawTime = System.nanoTime() + drawInterval;
    double delta = 0;//time control
    long lastTime = System.nanoTime();
    long currentTime;

    enemies.add(new Slime(this, background, player.x));

    //while(gameThread!=null){
    while(running){
      
      currentTime = System.nanoTime();

      delta += (currentTime - lastTime)/drawInterval;
      lastTime = currentTime;

      if(delta>=spritehreshold){
        repaint();
        update();
        delta--;
      }
    }
  }
  
  public void stop() {
    running = false;
    window.dispose();
  }

  public void startGameThread(){
    
    gameThread = new Thread(this);
    gameThread.start();
  }
}

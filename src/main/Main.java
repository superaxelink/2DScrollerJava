package main;

import javax.swing.JFrame;

public class Main {
    public static JFrame window;
    public static void main(String[] args){
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit the app when closing the window
        window.setResizable(false);
        window.setTitle("Test Game");

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        //gamePanel.config.loadConfig();
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
        
    }
}

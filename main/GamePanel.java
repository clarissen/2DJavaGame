package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;

// subclass of JPanel with some concrete choices
public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile size
    final int scale = 4;

    // needs to be public for access in Player class
    public final int tileSize = originalTileSize * scale; // 64x64 actual tile size

    // to decide how big our game screen is we can decide the number of tiles
    final int maxScreenCol = 4 * scale; // 16
    final int maxScreenRow = 3 * scale; // 12

    final int screenWidth = tileSize * maxScreenCol; // 64 * 16 =
    final int screenHeight = tileSize * maxScreenRow; // 64 * 12 = 

    // FPS
    int FPS = 60;

    // keyboard inputs
    KeyHandler keyH = new KeyHandler();
    
    // runs something at some rate
    Thread gameThread; 

    // Player class
    Player player = new Player(this,keyH);

    // creating a Constructor of GamePanel 
    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        // better rendering performance, comes with JPanel
        this.setDoubleBuffered(true); 
        // calling an instance of KeyHandler subclass of KeyListener
        this.addKeyListener(keyH);
        // GamePanel is the focus to receive key input
        this.setFocusable(true);


    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

    }

    public void sleepTimer(){
            // setting up the interval to draw and when to draw
            double drawInterval = 1000000000/FPS; // 0.016... seconds
            double nextDrawTime = System.nanoTime() + drawInterval;
    
            // this keeps the game running
            while(gameThread != null){
                // long currentTime = System.currentTimeMillis();
    
                // 1 UPDATE: e.g. update character position
                update();
    
                // 2 DRAW: e.g. draw the screen with updated information
                repaint();
    
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
    
                if(remainingTime < 0){
                    remainingTime = 0;
                }
    
                try {
                    Thread.sleep((long) remainingTime);
    
                    nextDrawTime += drawInterval; 
    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    public void deltaTimer(boolean FPSCheck){

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;

            }
            if(FPSCheck == true){

                if(timer >= 1000000000){
                    System.out.println("FPS:" + drawCount);
                    drawCount = 0;
                    timer = 0;
                }
            }
        }

    }

    @Override
    public void run() {

        // sleepTimer();
        deltaTimer(false);

    }

    // updating player coordinates
    public void update(){

        player.update();

    }

    // Character object
    public void paintComponent(Graphics g){
        
        // super accesses something from parent class, e.g. GamePanel gets from JPanel
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        player.draw(g2);

        g2.dispose();

    }
}


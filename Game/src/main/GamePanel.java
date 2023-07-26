package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    private final int originalTileSize = 16;
    private final int scale = 3;

    private final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16;
    private final int maxScreenRow = 12;
    private final int screenWidth = tileSize * maxScreenCol;
    private final int screenHeight = tileSize * maxScreenRow;

    private int carX = tileSize*10;
    private int carY = tileSize*8;
    private String currentMap = "parking";



    KeyHandler keyH = new KeyHandler();



    Thread GameThread;
    Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this);


    int FPS = 60;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        startGameThread();
        player.setDefaultValues();

        tileManager.loadMap();
    }

    public void startGameThread() {
        GameThread = new Thread(this);
        GameThread.start();

    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(GameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        if(currentMap == "parking"){
            tileManager.draw(g2, player.getPlayerState(), getCarX(), getCarY());
        } else if (currentMap == "interior") {
            tileManager.drawInterior(g2);
        }
        player.draw(g2);
        g2.dispose();
    }




    //getter and setter
    public int getTileSize() {
        return tileSize;
    }

    public int getMaxScreenCol() {
        return maxScreenCol;
    }

    public int getMaxScreenRow() {
        return maxScreenRow;
    }

    public int getCarX() {
        return carX;
    }

    public void setCarX(int carX) {
        this.carX = carX;
    }

    public int getCarY() {
        return carY;
    }

    public void setCarY(int carY) {
        this.carY = carY;
    }

    public void setCurrentMap(String currentMap) {
        this.currentMap = currentMap;
    }

}

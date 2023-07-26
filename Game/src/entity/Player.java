package entity;

import main.KeyHandler;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    private String imgFolder = "player/patryk/";
    private int characterWidth;
    private String playerState = "player";


    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        characterWidth = gp.getTileSize();

        getPlayerImage(imgFolder);
    }

    public String getPlayerState() {
        return playerState;
    }

    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 2;
        direction = "right";
    }

    public void changeMap(){
        gp.setCurrentMap("interior");
    }

    public void getCar(){
        speed = 4;
        x = gp.getCarX();
        y = gp.getCarY();
        imgFolder = "cars/panda/";
        getPlayerImage(imgFolder);
        characterWidth *= 2;
        playerState = "car";
    }

    public void outCar(){
        speed = 2;
        imgFolder = "player/patryk/";
        getPlayerImage(imgFolder);
        characterWidth /= 2;
        playerState = "player";
        gp.setCarX(x);
        gp.setCarY(y);
    }

    public void getPlayerImage(String folder) {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/" + folder + "left_2.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void update(){
        if(keyH.isUpPressed()){
            direction = "up";
            y -= speed;
        }
        if(keyH.isDownPressed()){
            direction = "down";
            y += speed;
        }
        if(keyH.isLeftPressed()){
            direction = "left";
            x -= speed;
        }
        if(keyH.isRightPressed()){
            direction = "right";
            x += speed;
        }
        if(keyH.iseTyped()){
            if(playerState.equals("player")){
                if((gp.getCarX()-40<x && x<gp.getCarX()+40) && (gp.getCarY()-40<y && y<gp.getCarY()+40)){
                    getCar();
                }
            }
            else if(playerState.equals("car")){
                outCar();
            }

            keyH.seteTyped(false);
        }
        if(keyH.isfTyped()){
            if((14*gp.getTileSize()<x && x<16*gp.getTileSize()) && (8*gp.getTileSize()<y && y<10*gp.getTileSize())){
                changeMap();
            }
        }


        if(keyH.isUpPressed() || keyH.isDownPressed() || keyH.isLeftPressed() || keyH.isRightPressed()){
            spriteCounter++;
            if(spriteCounter > 13){
                if(spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }


    }



    public void draw(Graphics2D g2){
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image =up2;
                }
                break;
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, characterWidth, gp.getTileSize(), null);


    }
}

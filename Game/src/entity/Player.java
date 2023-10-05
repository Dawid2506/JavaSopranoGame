package entity;

import inventory.OpenFridge;
import main.KeyHandler;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public OpenFridge openFridge;
    private String imgFolder = "player/patryk/";
    private int characterWidth;
    private String playerState = "player";
    private String playerMap = "parking";

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
        //default == 2
        speed = 5;
        direction = "right";
    }

    private void goInside(){
        gp.setCurrentMap("interior");
        playerMap = "interior";
        x = 100;
        y = 100;
    }

    private void goOutside(){
        gp.setCurrentMap("parking");
        playerMap = "parking";
        x = 100;
        y = 100;
    }

    private void getCar(){
        speed = 4;
        x = gp.getCarX();
        y = gp.getCarY();
        imgFolder = "cars/panda/";
        getPlayerImage(imgFolder);
        characterWidth *= 2;
        playerState = "car";
    }

    private void outCar(){
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

        if(playerMap.equals("parking")){
            if(keyH.isfTyped()){
                if((13*gp.getTileSize()<x && x<15*gp.getTileSize()) && (7*gp.getTileSize()<y && y<9*gp.getTileSize())){
                    goInside();
                }
            }
        }
        else if(playerMap.equals("interior")){
            if(keyH.isfTyped()){
                if(0<x && x<gp.getTileSize() && 0<y && y<gp.getTileSize()){
                    goOutside();
                }
            }
        }
        if(keyH.isfTyped()){
            gp.setFridgeState(false);
        }
        if(playerMap.equals("interior")){
            if(keyH.isfTyped()){
                if(x>140 && x<250 && y>30 && y<90){
                        System.out.println("success");
                        openFridge = new OpenFridge("silverLarge");
                        gp.setFridgeState(true);
                }
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

        image = switch (direction) {
            case "up" -> spriteNum == 1 ? up1 : up2;
            case "down" -> spriteNum == 1 ? down1 : down2;
            case "left" -> spriteNum == 1 ? left1 : left2;
            case "right" -> spriteNum == 1 ? right1 : right2;
            default -> null; // Provide a default value in case none of the cases match
        };
        g2.drawImage(image, x, y, characterWidth, gp.getTileSize(), null);

        //System.out.println("X:"+x+"     Y:"+y);

    }
}

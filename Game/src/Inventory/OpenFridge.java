package Inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OpenFridge {
    private String Type;
    private BufferedImage silverLargeImg;

    public OpenFridge(String type) {
        this.Type = type;
        try {
            silverLargeImg = ImageIO.read(getClass().getResourceAsStream("/inventory/freezerInventoryINWORK.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(silverLargeImg, 184, 138, 400, 300, null);
    }
}
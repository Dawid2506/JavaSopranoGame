package inventory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainInventory {
    BufferedImage border;


    public MainInventory() {
        try {
            border = ImageIO.read(getClass().getResourceAsStream("/inventory/inventoryBorder.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
        g2.drawImage(border, 630, 10, 127, 58, null);
    }
}

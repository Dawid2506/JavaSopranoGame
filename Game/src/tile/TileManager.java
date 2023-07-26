package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {


    GamePanel gp;
    Tile[] tile;

    public String[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new String [gp.getMaxScreenCol()] [gp.getMaxScreenRow()];

        getTileImage();
    }

    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/parkingMap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;
            String line;

            while(col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()){
                line = br.readLine();

                for(int i = 0; i < gp.getMaxScreenCol(); i++){
                    mapTileNum[i][row] = String.valueOf(line.charAt(i));

                }

                row++;


            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        printMapTileNum();

    }

    public void printMapTileNum() {
        for (int row = 0; row < gp.getMaxScreenRow(); row++) {
            for (int col = 0; col < gp.getMaxScreenCol(); col++) {
                System.out.print(mapTileNum[col][row] + " ");
            }
            System.out.println();
        }
    }

    public void getTileImage(){
        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/redRoof.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/blackRoof.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/greyWall.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/garageDoor.png"));

            tile[6] = new Tile();
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/mainDoor.png"));

            tile[7] = new Tile();
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[8] = new Tile();
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/cars/panda/right_1.png"));

            tile[9] = new Tile();
            tile[9].image = ImageIO.read(getClass().getResourceAsStream("/maps/interiorMap.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void draw(Graphics2D g2, String playerState ,int carX, int carY) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (row < gp.getMaxScreenRow()) {
            col = 0;

            while (col < gp.getMaxScreenCol()) {
                int tileNum = 0;

                if (mapTileNum[col][row] != null) {
                    tileNum = Integer.parseInt(mapTileNum[col][row]);
                }

                g2.drawImage(tile[tileNum].image, x, y, gp.getTileSize(), gp.getTileSize(), null);
                col++;
                x += gp.getTileSize();
            }

            row++;
            y += gp.getTileSize();
            x = 0;
        }

        if(playerState.equals("player")){
            g2.drawImage(tile[8].image, carX, carY, 2*gp.getTileSize(), gp.getTileSize(), null);
        }
    }

    public void drawInterior(Graphics2D g2) {
        g2.drawImage(tile[9].image, 0, 0, 16*gp.getTileSize(), 12*gp.getTileSize(), null);
    }
}


package Tiles;

import Main.GamePanel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int mapTileNumber[][];

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[10];
        mapTileNumber = new int[gp.maxScreenCol][gp.maxScreenRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage(){

        try{
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/grass01.png")); // Grass
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/wall.png")); // Wall
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/water00.png")); // Water


        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(String path){
        try{
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxScreenCol && row < gp.maxScreenRow){
                String line = br.readLine();

                while(col < gp.maxScreenCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col == gp.maxScreenCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNumber[col][row];

            g2.drawImage(tile[tileNum].image,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x += gp.tileSize;

            if(col == gp.maxScreenCol){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}

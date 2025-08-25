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

    int numberOfTiles = 37; // CHANGE THIS IF ADDING OR REMOVING ANY TILES IN TILE FOLDER

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[numberOfTiles];
        mapTileNumber = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/bigmap.csv");
    }

    //Tile loader || Add tiles to tile folder, update length of array.
    // This function automatically loads all textures and stores them in an array.
    public void getTileImage(){

        try{
           for(int i = 0; i < numberOfTiles; i++){
               tile[i] = new Tile();
               String fileName = String.format("%03d.png", i); // "001.png", "002.png", etc.
               String pathToTile = "/tiles/" + fileName;
               tile[i].image = ImageIO.read(getClass().getResourceAsStream(pathToTile));
           }

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

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(",");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNumber[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
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

        int worldCol = 0;
        int worldRow = 0;


        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){
            int tileNum = mapTileNumber[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;



            g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
        }
    }
}

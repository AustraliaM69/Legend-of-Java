// Auto-generated TileManager code
// Copy this to src/Tiles/TileManager.java

package Tiles;

import Main.GamePanel;
import Utility.TileSheetGenerator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    TileSheet tileSheet;
    int[][] mapTileNum;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[45]; // Auto-sized
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
        tileSheet = new TileSheet("/tilesheet.png", 16, 12, 6);
        getTileImage();
        loadMap();
    }

    public void getTileImage(){
        tile[0] = new Tile(0, 0, false); // earth
        tile[1] = new Tile(1, 0, false); // floor01
        tile[2] = new Tile(2, 0, false); // grass00
        tile[3] = new Tile(3, 0, false); // grass01
        tile[4] = new Tile(4, 0, true); // hut
        tile[5] = new Tile(5, 0, false); // road00
        tile[6] = new Tile(6, 0, false); // road01
        tile[7] = new Tile(7, 0, false); // road02
        tile[8] = new Tile(8, 0, false); // road03
        tile[9] = new Tile(9, 0, false); // road04
        tile[10] = new Tile(10, 0, false); // road05
        tile[11] = new Tile(11, 0, false); // road06
        tile[12] = new Tile(0, 1, false); // road07
        tile[13] = new Tile(1, 1, false); // road08
        tile[14] = new Tile(2, 1, false); // road09
        tile[15] = new Tile(3, 1, false); // road10
        tile[16] = new Tile(4, 1, false); // road11
        tile[17] = new Tile(5, 1, false); // road12
        tile[18] = new Tile(6, 1, true); // table01
        tile[19] = new Tile(7, 1, true); // tree
        tile[20] = new Tile(8, 1, true); // wall
        tile[21] = new Tile(9, 1, true); // water00
        tile[22] = new Tile(10, 1, true); // water01
        tile[23] = new Tile(11, 1, true); // water02
        tile[24] = new Tile(0, 2, true); // water03
        tile[25] = new Tile(1, 2, true); // water04
        tile[26] = new Tile(2, 2, true); // water05
        tile[27] = new Tile(3, 2, true); // water06
        tile[28] = new Tile(4, 2, true); // water07
        tile[29] = new Tile(5, 2, true); // water08
        tile[30] = new Tile(6, 2, true); // water09
        tile[31] = new Tile(7, 2, true); // water10
        tile[32] = new Tile(8, 2, true); // water11
        tile[33] = new Tile(9, 2, true); // water12
        tile[34] = new Tile(10, 2, true); // water13
    }

    public void loadMap() {
        // TODO: Load from Tiled map file
        for (int col = 0; col < gp.maxScreenCol; col++) {
            for (int row = 0; row < gp.maxScreenRow; row++) {
                mapTileNum[col][row] = 0; // Default to grass
            }
        }
    }

    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow){
            int tileNum = mapTileNum[col][row];
            if (tileNum >= 0 && tileNum < tile.length && tile[tileNum] != null) {
                BufferedImage tileImage = tileSheet.getTile(tile[tileNum].sheetX, tile[tileNum].sheetY);
                if (tileImage != null) {
                    g2.drawImage(tileImage, x, y, gp.tileSize, gp.tileSize, null);
                }
            }
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

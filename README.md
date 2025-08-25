# Legend of Java

A Java-based 2D game engine with sprite sheet and tile sheet support.

## Features

- **Sprite Sheet Loading**: Automatic loading and management of sprite sheets
- **Tile Sheet System**: Efficient tile management with automatic tile sheet generation
- **Animation System**: Support for directional animations (idle, walk up, down, left, right) with unique sprites for each direction
- **Smooth Movement**: Player movement with WASD keys
- **Scalable Graphics**: 3x scaling from 16x16 pixel sprites to 48x48 pixels
- **Collision Detection**: Tile-based collision system
- **Dynamic Map Loading**: Support for custom maps and tile configurations

## Project Structure

```
Legend of Java/
├── src/
│   ├── Entity/
│   │   ├── Entity.java          # Base entity class with sprite support
│   │   └── Player.java          # Player class with animation
│   ├── Main/
│   │   ├── GamePanel.java       # Main game panel
│   │   ├── KeyHandler.java      # Input handling
│   │   └── Main.java           # Game entry point
│   ├── Tiles/
│   │   ├── Tile.java           # Individual tile class
│   │   ├── TileManager.java    # Tile management and rendering
│   │   └── TileSheet.java      # Tile sheet loading utility
│   └── Utility/
│       ├── SpriteSheet.java     # Sprite sheet loading utility
│       ├── SpriteSheetGenerator.java # Tool to generate placeholder sprites
│       ├── TileSheetGenerator.java # Automatic tile sheet generation
│       └── TileConfig.java     # Dynamic tile configuration system
├── res/
│   ├── player_spritesheet.png   # Player sprite sheet
│   ├── player_spritesheet_info.txt # Sprite sheet documentation
│   ├── tilesheet.png           # Generated tile sheet
│   ├── tiles.conf              # Tile configuration file (optional)
│   └── [individual tile images] # Source tile images
└── README.md
```

## Tile Sheet System

The game now uses an efficient tile sheet system that automatically combines individual tile images into a single optimized sheet.

### Current Tile Capacity
- **Single Sheet**: 12×6 = **72 tiles** (upgraded from 50)
- **Multiple Sheets**: **Unlimited** (as many as you want)
- **Dynamic Config**: **Unlimited** with automatic management

### Adding More Tiles

### 🚀 **NEW: Automatic Tile Management (Recommended)**

The easiest way to add tiles is using the **AutoTileManager** system. Just add your tile images to the `res/` folder and run one command!

#### **Step 1: Add Tile Images**
Simply place your new tile images in the `res/` folder:
```
res/
├── stone.png
├── sand.png
├── lava.png
├── ice.png
├── bridge.png
└── door.png
```

#### **Step 2: Run AutoTileManager**
```bash
javac -cp "src" src/Utility/AutoTileManager.java
java -cp "src" Utility.AutoTileManager
```

#### **Step 3: Copy Generated Code**
The system automatically generates:
- ✅ **Updated tile sheet** (`res/tilesheet.png`)
- ✅ **Tiled properties** (`res/tiled_properties.txt`)
- ✅ **TileManager code** (`res/tilemanager_template.java`)
- ✅ **Auto-config file** (`res/auto_tiles.conf`)

Just copy the generated code from `res/tilemanager_template.java` to `src/Tiles/TileManager.java`!

#### **What AutoTileManager Does Automatically:**
- 🔍 **Scans** the `res/` folder for new tile images
- 🎯 **Auto-detects** collision properties based on tile names
- 📐 **Calculates** correct tile sheet positions
- 📝 **Generates** all configuration files
- 💻 **Creates** ready-to-use TileManager code
- 🏷️ **Assigns** proper tile IDs and descriptions

#### **Smart Collision Detection:**
The system automatically determines collision based on tile names:
- **Collision = true**: `wall`, `tree`, `hut`, `table`, `stone`, `lava`, `door`, `chest`, `water`
- **Collision = false**: `grass`, `road`, `earth`, `floor`, `sand`, `ice`, `bridge`, `sign`

### **Example: Adding a "Stone" Tile**

1. **Add** `stone.png` to `res/` folder
2. **Run**: `java -cp "src" Utility.AutoTileManager`
3. **Copy** the generated code to `TileManager.java`
4. **Done!** The stone tile is now available in your game

---

### **Legacy Methods (Manual)**

#### Option 1: Simple Method (Manual)

1. **Add tile images** to the `res/` folder:
   ```
   res/
   ├── stone.png
   ├── sand.png
   ├── lava.png
   ├── ice.png
   ├── bridge.png
   └── door.png
   ```

2. **Update the tile order** in `src/Utility/TileSheetGenerator.java`:
   ```java
   private static final String[] TILE_ORDER = {
       // Grass tiles
       "grass00", "grass01",
       // Water tiles
       "water00", "water01", "water02", "water03", "water04", "water05",
       "water06", "water07", "water08", "water09", "water10", "water11", "water12", "water13",
       // Road tiles
       "road00", "road01", "road02", "road03", "road04", "road05",
       "road06", "road07", "road08", "road09", "road10", "road11", "road12",
       // Other tiles
       "earth", "floor01", "wall", "tree", "hut", "table01",
       // New tiles - add your new tiles here:
       "stone", "sand", "lava", "ice", "bridge", "door", "chest", "sign"
   };
   ```

3. **Regenerate the tile sheet**:
   ```bash
   javac -cp "src" src/Utility/TileSheetGenerator.java
   java -cp "src" Utility.TileSheetGenerator
   ```

4. **Add tiles to TileManager** in `src/Tiles/TileManager.java`:
   ```java
   // New tiles - add your new tiles here:
   tile[35] = new Tile(5, 3, true);  // stone (collision)
   tile[36] = new Tile(6, 3, false); // sand (no collision)
   tile[37] = new Tile(7, 3, true);  // lava (collision)
   tile[38] = new Tile(8, 3, false); // ice (no collision)
   tile[39] = new Tile(9, 3, false); // bridge (no collision)
   tile[40] = new Tile(10, 3, true); // door (collision)
   tile[41] = new Tile(11, 3, true); // chest (collision)
   tile[42] = new Tile(0, 4, false); // sign (no collision)
   ```

#### Option 2: Multiple Tile Sheets (For 100+ tiles)

1. **Create multiple tile sheet files**:
   ```
   res/
   ├── tilesheet.png    # Main tiles (terrain, basic objects)
   ├── tilesheet2.png   # Additional tiles (decorations, special objects)
   └── tilesheet3.png   # More tiles (UI elements, effects)
   ```

2. **Load them in TileManager**:
   ```java
   tileSheet = new TileSheet("/tilesheet.png", 16, 12, 6);
   tileSheet2 = new TileSheet("/tilesheet2.png", 16, 12, 6);
   tileSheet3 = new TileSheet("/tilesheet3.png", 16, 12, 6);
   ```

3. **Use different tile arrays** for each sheet:
   ```java
   Tile[] terrainTiles = new Tile[72];  // From tilesheet.png
   Tile[] objectTiles = new Tile[72];   // From tilesheet2.png
   Tile[] uiTiles = new Tile[72];       // From tilesheet3.png
   ```

#### Option 3: Dynamic Configuration (Advanced)

The `TileConfig` system allows automatic tile management through configuration files.

1. **Create a tile configuration file** (`res/tiles.conf`):
   ```
   # Tile Configuration File
   # Format: name,sheetX,sheetY,collision,description
   # Example: grass00,0,0,false,Basic grass tile
   
   grass00,0,0,false,Basic grass tile
   grass01,1,0,false,Grass tile variant
   water00,2,0,true,Water tile
   wall,1,3,true,Solid wall
   stone,5,3,true,Rock formation
   sand,6,3,false,Sandy ground
   ```

2. **Load configuration in your game**:
   ```java
   TileConfig.loadConfig();
   Map<String, TileConfig.TileInfo> allTiles = TileConfig.getAllTiles();
   ```

3. **Add tiles programmatically**:
   ```java
   TileConfig.addTile("lava", 7, 3, true, "Hot lava");
   TileConfig.addTile("ice", 8, 3, false, "Slippery ice");
   ```

### Tile Sheet Generation

The `TileSheetGenerator` automatically:
- Combines all PNG files in the `res/` folder into a single tile sheet
- Maintains proper tile ordering based on the `TILE_ORDER` array
- Skips non-tile files (like player spritesheets)
- Provides detailed logging of the generation process

**Generate a new tile sheet**:
```bash
javac -cp "src" src/Utility/TileSheetGenerator.java
java -cp "src" Utility.TileSheetGenerator
```

### Tile Properties

Each tile can have different properties:
- **Collision**: `true` = solid, `false` = walkable
- **Position**: `sheetX, sheetY` coordinates in the tile sheet
- **Description**: Optional text description for the tile

### Example: Adding a "Stone" Tile

```java
// 1. Add stone.png to res/ folder
// 2. Add "stone" to TILE_ORDER array
// 3. Regenerate tile sheet
// 4. Add to TileManager:
tile[35] = new Tile(5, 3, true); // stone (with collision)
```

## Sprite Sheet System

### Sprite Sheet Format

The game expects sprite sheets in the following format:
- **Sprite Size**: 16x16 pixels (original size)
- **Layout**: 5 rows × 4 columns = 20 sprites total
- **Row 0**: Idle animations (4 frames)
- **Row 1**: Walking down animations (4 frames)
- **Row 2**: Walking left animations (4 frames)
- **Row 3**: Walking right animations (4 frames)
- **Row 4**: Walking up animations (4 frames)

### Using Custom Sprite Sheets

1. Create a sprite sheet following the format above
2. Place it in the `res/` folder
3. Update the path in `Player.java` if needed:

```java
SpriteSheet spriteSheet = new SpriteSheet("/res/your_spritesheet.png", 16, 16);
```

### Generating Placeholder Sprites

If you need placeholder sprites for testing, run:

```bash
cd src
javac -cp . Utility/SpriteSheetGenerator.java
java -cp . Utility.SpriteSheetGenerator
```

This will generate a simple placeholder sprite sheet in the `res/` folder.

## Running the Game

### Compile and Run

```bash
javac -cp src src/Main/Main.java
java -cp src Main.Main
```

### Controls

- **W**: Move up
- **S**: Move down
- **A**: Move left
- **D**: Move right

## Technical Details

### TileSheet Class

The `TileSheet` class provides methods to:
- Load tile sheets from file system or resources
- Extract individual tiles by row/column or index
- Handle different tile sizes and sheet dimensions

### TileManager Class

The `TileManager` class handles:
- Tile sheet loading and management
- Map data storage and rendering
- Collision detection setup
- Tile property management

### SpriteSheet Class

The `SpriteSheet` class provides methods to:
- Load sprite sheets from file system or resources
- Extract individual sprites by row/column or index
- Handle different sprite sizes

### Animation System

The animation system includes:
- **Frame-based animation**: Cycles through sprite frames
- **Directional animations**: Different sprites for each movement direction
- **Idle animations**: Sprites for when the player is not moving
- **Configurable speed**: Adjust animation speed via `animationSpeed` property

### Entity Base Class

The `Entity` class provides:
- Position and movement properties
- Sprite and animation properties
- Direction tracking
- Movement state tracking

## Extending the System

### Adding New Entities

1. Create a new class extending `Entity`
2. Implement sprite loading in constructor
3. Override `update()` and `draw()` methods as needed

### Adding New Animation Types

1. Add new sprite arrays to your entity class
2. Load sprites from additional rows in your sprite sheet
3. Update the animation logic in `updateAnimation()`

### Custom Sprite Sizes

To use different sprite sizes:
1. Update the sprite size parameter in `SpriteSheet` constructor
2. Adjust the scaling in the `draw()` method
3. Update the sprite sheet layout accordingly

### Organizing Tiles by Category

You can organize tiles into categories for better management:

```java
// Terrain tiles (0-19)
tile[0] = new Tile(0, 0, false); // grass00
tile[1] = new Tile(1, 0, false); // grass01
// ... more grass tiles

// Water tiles (20-39)
tile[20] = new Tile(2, 0, true); // water00
tile[21] = new Tile(3, 0, true); // water01
// ... more water tiles

// Road tiles (40-59)
tile[40] = new Tile(6, 1, false); // road00
tile[41] = new Tile(7, 1, false); // road01
// ... more road tiles

// Objects (60+)
tile[60] = new Tile(1, 3, true);  // wall
tile[61] = new Tile(2, 3, true);  // tree
// ... more objects
```

## Troubleshooting

### Tile Sheet Not Loading

- Ensure the tile sheet file exists in the `res/` folder
- Check that the file path in the code matches the actual file location
- Verify the tile sheet dimensions match the expected size (12×6)

### Tile Generation Issues

- Check that all tile images are 16×16 pixels
- Ensure tile names in `TILE_ORDER` match the actual file names
- Verify that the tile sheet size can accommodate all tiles

### Sprite Sheet Not Loading

- Ensure the sprite sheet file exists in the `res/` folder
- Check that the file path in the code matches the actual file location
- Verify the sprite sheet format matches the expected layout

### Animation Issues

- Check that sprite arrays are properly initialized
- Verify that sprite indices are within bounds
- Ensure animation speed is set to a reasonable value

### Performance Issues

- Consider reducing animation speed for better performance
- Optimize tile sheet size if using large tiles
- Use appropriate tile sizes for your game's scale
- Consider using multiple tile sheets for very large tile sets

## Future Enhancements

### Planned Features

1. **Tiled Map Editor Integration**: Load maps from Tiled (.tmx) files
2. **Multiple Tile Layers**: Support for ground, objects, and effects layers
3. **Tile Animation**: Animated tiles (water, lava, etc.)
4. **Tile Variants**: Random tile variations for natural-looking terrain
5. **Tile Properties**: Extended properties like damage, healing, teleportation

### Tiled Integration Roadmap

1. **Phase 1**: Basic .tmx file parsing
2. **Phase 2**: Multiple layer support
3. **Phase 3**: Object layer support
4. **Phase 4**: Advanced tile properties

The tile sheet system provides a solid foundation for building complex 2D games with efficient resource management and easy extensibility.

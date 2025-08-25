# Legend of Java

A Java-based 2D game engine with sprite sheet support.

## Features

- **Sprite Sheet Loading**: Automatic loading and management of sprite sheets
- **Animation System**: Support for directional animations (idle, walk up, down, left, right) with unique sprites for each direction
- **Smooth Movement**: Player movement with WASD keys
- **Scalable Graphics**: 3x scaling from 16x16 pixel sprites to 48x48 pixels

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
│   └── Utility/
│       ├── SpriteSheet.java     # Sprite sheet loading utility
│       └── SpriteSheetGenerator.java # Tool to generate placeholder sprites
├── res/
│   ├── player_spritesheet.png   # Player sprite sheet
│   └── player_spritesheet_info.txt # Sprite sheet documentation
└── README.md
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

## Troubleshooting

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
- Optimize sprite sheet size if using large sprites
- Use appropriate sprite sizes for your game's scale

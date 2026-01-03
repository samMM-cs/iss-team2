package com.game.view.mapview;

import java.util.List;

import com.game.model.GameState;
import com.game.model.Position;
import com.game.model.character.HasSpriteAndPosition;
import com.game.model.map.LayerData;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

public class MapView extends Pane {
    private static final double SMOOTHING = .18;
    private List<LayerData> layers;
    private int spriteLayerIndex;
    private int tileSize;
    private int renderedTileSize;
    private int width;
    private int height;
    private boolean[][] walkableTiles;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private int tileSetCols;
    private Image tileSet;
    private Position playerPosition = Position.Origin;
    private Position lastOffset = Position.Origin;

    public MapView(List<LayerData> layers, int spriteLayerIndex, int tileSize, int width, int height,
            boolean[][] walkableTiles, int renderedTileSize, Image tileSet) {
        this.layers = layers;
        this.spriteLayerIndex = spriteLayerIndex;
        this.tileSize = tileSize;
        this.renderedTileSize = renderedTileSize;
        this.width = width;
        this.height = height;
        this.walkableTiles = walkableTiles;
        this.tileSet = tileSet;
        this.tileSetCols = (int) tileSet.getWidth() / this.tileSize;
        this.canvas = new Canvas(renderedTileSize * width, renderedTileSize * height);
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    @Override
    protected void layoutChildren() {
        updateSpritePositions();
        super.layoutChildren();
        canvas.setWidth(getWidth());
        canvas.setHeight(getHeight());
        Position targetOffset = calculateCameraOffset(getWidth(), getHeight());
        // On first layout, snap immediately to the target to start centered/snapped
        if (lastOffset.equals(Position.Origin) && !targetOffset.equals(Position.Origin)) {
            lastOffset = targetOffset;
        }
        Position offset = lastOffset.scale(1 - SMOOTHING).add(targetOffset.scale(SMOOTHING));
        drawMap(offset);
        this.lastOffset = offset;

    }

    public void updateSpritePositions() {
        for (HasSpriteAndPosition spritePosition : GameState.getInstance().getSpritesAndPositions()) {
            if (spritePosition.getSprite() != null) {
                spritePosition.getSprite().setFitWidth(renderedTileSize);
                spritePosition.getSprite().setFitHeight(renderedTileSize);
                spritePosition.getSprite().setX(lastOffset.x() + spritePosition.getPosition().x() * renderedTileSize);
                spritePosition.getSprite().setY(lastOffset.y() + spritePosition.getPosition().y() * renderedTileSize);
            }
        }
    }

    private Position calculateCameraOffset(double viewWidth, double viewHeight) {
        // Build Position objects for viewport and map size
        Position viewport = new Position(viewWidth, viewHeight);
        Position mapSize = new Position(this.width, this.height).scale(renderedTileSize);

        // targetOffset centers the player in the viewport
        Position target = viewport.scale(.5).sub(playerPosition).clamp(viewport.sub(mapSize), Position.Origin);

        return target;
    }

    private void drawMap(Position offset) {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < layers.size(); i++) {
            if (i == spriteLayerIndex) {
                renderSprites(offset);
            }
            renderLayer(layers.get(i), offset);
        }
    }

    private void renderSprites(Position offset) {
        for (HasSpriteAndPosition spritePosition : GameState.getInstance().getSpritesAndPositions()) {
            ImageView sprite = spritePosition.getSprite();
            Position position = spritePosition.getPosition();
            Image img = sprite.getImage();
            Rectangle2D vp = sprite.getViewport();
            Position dest = new Position(sprite.getX(), sprite.getY());
            if (dest.equals(Position.Origin))
                dest = offset.add(position.scale(renderedTileSize));

            graphicsContext.drawImage(img,
                    vp.getMinX(), vp.getMinY(),
                    vp.getWidth(), vp.getHeight(),
                    dest.x(), dest.y(),
                    renderedTileSize, renderedTileSize);

        }
    }

    private void renderLayer(LayerData layer, Position offset) {
        int[] tiles = layer.getData();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int mapIndex = x + y * width;
                if (mapIndex >= tiles.length)
                    continue;
                int tile = tiles[mapIndex] - 1;
                if (tile < 0)
                    continue;
                int sourceX = tileSize * (tile % tileSetCols);
                int sourceY = tileSize * (tile / tileSetCols);
                graphicsContext.drawImage(
                        tileSet, // image to take tiles from
                        sourceX, sourceY, // coords in source img
                        tileSize, tileSize, // size in source img
                        offset.x() + x * renderedTileSize,
                        offset.y() + y * renderedTileSize, // coords in graphics context
                        renderedTileSize, renderedTileSize); // size in graphics context
            }
        }
    }

    public int getTileSize() {
        return renderedTileSize;
    }

    public double getMapWidth() {
        return width * renderedTileSize;
    }

    public double getMapHeight() {
        return height * renderedTileSize;
    }

    public Position getOffset() {
        return lastOffset;
    }

    public boolean[][] getWalkableTiles() {
        return walkableTiles;
    }

    public void updatePlayerPosition(Position pos) {
        this.playerPosition = pos;
        requestLayout();
    }
}

package com.game.controller.exploration;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Position;
import com.game.model.map.LayerData;
import com.game.model.map.SpritePosition;
import com.game.model.map.TiledMapData;
import com.game.view.mapview.MapView;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapBuilder {
    private List<LayerData> layers = new ArrayList<>();
    private List<SpritePosition> sprites = new ArrayList<>();
    private List<Integer> walkableId;
    private int spriteLayerIndex;
    private int tileSize, width, height;
    private boolean[][] walkableTiles;
    private int renderedTileSize = 50;
    private Image tileSet;

    public static TiledMapData loadRawMapData(String mapFilePath) {
        TiledMapData mapData;
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = MapBuilder.class.getResourceAsStream(mapFilePath)) {
            mapData = mapper.readValue(is, TiledMapData.class);
            return mapData;
        } catch (Exception e) {
            System.err.println(
                    "Error while loading TMJ data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public MapBuilder() {

    }

    public MapBuilder setTileSetImageFromPath(String imagePath) {
        try (InputStream is = MapBuilder.class.getResourceAsStream(imagePath)) {
            this.tileSet = new Image(is);
        } catch (Exception e) {
            System.err.println("Error while loading the TileSetImage: " + e.getMessage());
            e.printStackTrace();
        }
        return this;
    }

    public MapBuilder setWalkableIds(String path) {
        try {
            URL url = MapBuilder.class.getResource(path);
            String regex = "<tile id=\"(\\d*)\">\\s*<properties>\\s*<property name=\"walkable\" type=\"bool\" value=\"true\"/>\\s*</properties>\\s*</tile>";
            String content = new String(((BufferedInputStream) url.getContent()).readAllBytes());
            walkableId = Pattern.compile(regex)
                    .matcher(content)
                    .results()
                    .map(m -> Integer.parseInt(m.group(1)))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error while loading walkable ids");
            e.printStackTrace();
        }
        return this;
    }

    public MapBuilder setDimensions(int tileSize, int width, int height) {
        this.tileSize = tileSize;
        this.width = width;
        this.height = height;
        return this;
    }

    public MapBuilder setRenderedTileSize(int renderedTileSize) {
        this.renderedTileSize = renderedTileSize;
        return this;
    }

    public MapBuilder addLayer(LayerData layer) {
        this.layers.add(layer);
        return this;
    }

    public MapBuilder addSprite(ImageView sprite, Position position) {
        this.sprites.add(new SpritePosition(sprite, position));
        return this;
    }

    public MapBuilder showSprites() {
        spriteLayerIndex = layers.size();
        return this;
    }

    public MapView build() {
        walkableTiles = new boolean[this.width][this.height];
        for (int i = 0; i < this.spriteLayerIndex; i++) {
            int[] tiles = layers.get(i).getData();
            for (int y = 0; y < this.height; y++) {
                for (int x = 0; x < this.width; x++) {
                    int mapIndex = x + y * width;
                    if (mapIndex >= tiles.length || tiles[mapIndex] < 1)
                        continue;
                    // tile indices in .tmj are 1 based
                    // walkableTiles is indexed as [x][y]
                    walkableTiles[x][y] = walkableId != null && walkableId.contains(tiles[mapIndex] - 1);
                }
            }
        }
        return new MapView(layers, sprites, spriteLayerIndex, tileSize, width, height, walkableTiles,
                renderedTileSize, tileSet);
    }
}

package com.game.view.mapview;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Position;
import com.game.model.map.LayerData;
import com.game.model.map.TiledMapData;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class MapView extends Pane {
    private static final List<Integer> WALKABLE_IDS = getWalkableIds();
    private static final int RENDERED_TILE_SIZE = 40;

    private boolean[][] walkableTiles;
    private List<ImageView> dynamicSprites = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;
    private Image tileSetImage;

    private TiledMapData mapData;

    // Variabili dinamiche
    private int TILE_SIZE;
    private int mapWidthInTiles;
    private int mapHeightInTiles;
    private int tilesetCols; // Contiene le colonne del tileset

    // Variabili per il tracking del player e scrolling della camera
    private Position lastOffset = new Position(0, 0);
    private Position playerPosition = new Position(0, 0);

    public MapView(String mapFilePath, String tileSetImagePath) {
        // 1. Caricamento Dati Mappa e Tileset
        if (!loadMapData(mapFilePath)) {
            System.err.println("FATAL: MapView non inizializzata per errore di parsing dati.");
            return;
        }

        // Caricamento immagine (necessaria per il calcolo delle colonne)
        loadTileSetImage(tileSetImagePath);

        // --- 2. Inizializzazione basata sui dati caricati ---

        this.TILE_SIZE = mapData.getTilewidth();
        // Use fixed rendered tile size (override map's tilewidth for display)
        this.mapWidthInTiles = mapData.getWidth();
        this.mapHeightInTiles = mapData.getHeight();
        this.walkableTiles = new boolean[mapWidthInTiles][mapWidthInTiles];
        for (int i = 0; i < mapWidthInTiles; i++)
            for (int j = 0; j < mapHeightInTiles; j++)
                walkableTiles[i][j] = true;
        // Verifica se l'immagine è stata caricata e se TILE_SIZE è stato letto
        // correttamente.
        if (tileSetImage != null && tileSetImage.getWidth() > 0 && this.TILE_SIZE > 0) {
            // Formula: Larghezza Totale Immagine / Larghezza Singola Tessera
            int calculatedColumns = (int) (tileSetImage.getWidth() / this.TILE_SIZE);

            if (calculatedColumns > 0) {
                this.tilesetCols = calculatedColumns;
                System.out.println("Colonna calcolata dalla larghezza immagine: " + this.tilesetCols);
            } else {
                System.err.println(
                        "FATAL ERROR: Calcolo delle colonne fallito. TILE_SIZE o larghezza immagine non validi.");
                return;
            }
        } else {
            System.err.println(
                    "FATAL ERROR: Immagine Tileset non caricata o TILE_SIZE non valido (" + this.TILE_SIZE + ").");
            return;
        }

        // 3. Setup del Canvas (use rendered tile size for canvas dimensions)
        double canvasWidth = mapWidthInTiles * RENDERED_TILE_SIZE;
        double canvasHeight = mapHeightInTiles * RENDERED_TILE_SIZE;

        this.canvas = new Canvas(canvasWidth, canvasHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }

    public void addSprite(ImageView sprite) {
        dynamicSprites.add(sprite);
    }

    public void removeSprite(ImageView sprite) {
        dynamicSprites.remove(sprite);
    }

    public void renderSpritesWithLayers() {
        dynamicSprites.sort((s1, s2) -> Double.compare(s1.getY(), s2.getY()));

        getChildren().removeAll(dynamicSprites);
        getChildren().addAll(dynamicSprites);
    }

    private static List<Integer> getWalkableIds() {
        try {
            URL url = MapView.class.getResource("/maps/punyworld-overworld-tiles.tsx");
            String content = new String(((BufferedInputStream) url.getContent()).readAllBytes());
            return Pattern.compile(
                    "<tile id=\"(\\d*)\">\\s*<properties>\\s*<property name=\"walkable\" type=\"bool\" value=\"true\"/>\\s*</properties>\\s*</tile>")
                    .matcher(content)
                    .results()
                    .map(m -> Integer.parseInt(m.group(1))).collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("oopsies");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        final double paneWidth = getWidth();
        final double paneHeight = getHeight();

        // Ensure canvas matches pane size
        if (canvas.getWidth() != paneWidth || canvas.getHeight() != paneHeight) {
            canvas.setWidth(paneWidth);
            canvas.setHeight(paneHeight);
        }

        // Compute smoothed offsets toward target
        Position targetOffset = calculateCameraOffset(paneWidth, paneHeight);
        // double targetOffsetX = calculateCameraOffsetX(paneWidth);
        // double targetOffsetY = calculateCameraOffsetY(paneHeight);
        final double SMOOTHING = 0.18;
        Position offset = lastOffset.scale(1 - SMOOTHING).add(targetOffset.scale(SMOOTHING));
        // Draw map with computed offsets
        drawMap(offset);

        // Save state
        this.lastOffset = offset;
    }

    private Position calculateCameraOffset(double width, double height) {
        double x = Math.clamp(width / 2 - playerPosition.x(), width - mapWidthInTiles * RENDERED_TILE_SIZE,
                0);
        double y = Math.clamp(height / 2 - playerPosition.y(), height - mapHeightInTiles * RENDERED_TILE_SIZE,
                0);
        return new Position(x, y);
    }

    /** Carica il file TMJ come stream dal classpath. */
    private boolean loadMapData(String jsonPath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream(jsonPath)) {
            if (is == null) {
                System.err.println("ERRORE: File TMJ non trovato come risorsa: " + jsonPath);
                return false;
            }
            this.mapData = mapper.readValue(is, TiledMapData.class);
            System.out.println("Tiled della mappa TMJ caricati con successo!");
            return true;
        } catch (IOException e) {
            System.err.println(
                    "Errore nel caricamento dei dati della mappa TMJ. Controlla la struttura JSON: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /** Carica l'immagine del Tileset come stream dal classpath. */
    private void loadTileSetImage(String imagePath) {
        try (InputStream is = getClass().getResourceAsStream(imagePath)) {
            if (is == null) {
                System.err.println("ERRORE: Immagine Tileset non trovata come risorsa: " + imagePath);
                return;
            }
            this.tileSetImage = new Image(is);
            // Non c'è bisogno di stampare il successo qui, il controllo avviene dopo.
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine TileSet: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------
    // METODO DI DISEGNO (RENDERING)
    // ----------------------------------------------------------------------

    private void drawMap(Position offset) {
        if (mapData == null || tileSetImage == null || tilesetCols == 0) {
            // Pulisci il Canvas se qualcosa è fallito (lo rende trasparente)
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            return;
        }

        // Pulisci il Canvas prima di disegnare (essenziale se lo ridisegni)
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (LayerData layer : mapData.getLayers()) {
            if (!"tilelayer".equals(layer.getType())) {
                continue;
            }

            int[] tileIndices = layer.getData();
            final int TILE_ID_OFFSET = 1;

            for (int y = 0; y < mapHeightInTiles; y++) {
                for (int x = 0; x < mapWidthInTiles; x++) {

                    int mapIndex = x + y * mapWidthInTiles;
                    if (mapIndex >= tileIndices.length)
                        continue;

                    int tileIndex = tileIndices[mapIndex];

                    if (tileIndex >= TILE_ID_OFFSET) {
                        int tileID = tileIndex - TILE_ID_OFFSET;
                        walkableTiles[x][y] = WALKABLE_IDS.contains(tileID);
                        // 1. Calcolo SourceX e SourceY (rimane fisso, non dipende dalla scala)
                        int sourceX = (tileID % tilesetCols) * TILE_SIZE;
                        int sourceY = (tileID / tilesetCols) * TILE_SIZE;

                        // 2. Disegna la tessera ritagliata sul Canvas
                        gc.drawImage(
                                tileSetImage,
                                sourceX, sourceY, // Coordinate Sorgente (nel Tileset)
                                TILE_SIZE, TILE_SIZE, // Dimensione Sorgente (originale)

                                // Coordinate Destinazione (Scala applicata + Offset per centrare)
                                offset.x() + x * RENDERED_TILE_SIZE,
                                offset.y() + y * RENDERED_TILE_SIZE,

                                RENDERED_TILE_SIZE, RENDERED_TILE_SIZE // Dimensione Destinazione (scalata)
                        );
                    }
                }
            }
        }
    }
    // ----------------------------------------------------------------------
    // METODI GETTER (Per accedere alla vista)
    // ----------------------------------------------------------------------

    public int getTileSize() {
        return RENDERED_TILE_SIZE;
    }

    public double getMapWidth() {
        return mapWidthInTiles * RENDERED_TILE_SIZE;
    }

    public double getMapHeight() {
        return mapHeightInTiles * RENDERED_TILE_SIZE;
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
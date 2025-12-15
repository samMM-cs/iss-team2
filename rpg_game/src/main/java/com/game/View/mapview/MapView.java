package com.game.view.mapview;

import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.map.LayerData;
import com.game.model.map.TiledMapData;

import java.io.IOException;
import java.io.InputStream;

public class MapView extends Pane {
    private Canvas canvas;
    private GraphicsContext gc;
    private Image tileSetImage;

    private TiledMapData mapData;

    // Variabili dinamiche
    private int TILE_SIZE;
    private final int RENDERED_TILE_SIZE = 60;
    private int mapWidthInTiles;
    private int mapHeightInTiles;
    private int tilesetCols; // Contiene le colonne del tileset

    private double lastOffsetX;
    private double lastOffsetY;

    // Variabili per il tracking del player e scrolling della camera
    private double playerX;
    private double playerY;

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

    // MapView.java

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
        double targetOffsetX = calculateCameraOffsetX(paneWidth);
        double targetOffsetY = calculateCameraOffsetY(paneHeight);
        final double SMOOTHING = 0.18;
        double offsetX = lastOffsetX + (targetOffsetX - lastOffsetX) * SMOOTHING;
        double offsetY = lastOffsetY + (targetOffsetY - lastOffsetY) * SMOOTHING;

        // Draw map with computed offsets
        drawMap(offsetX, offsetY);

        // Save state
        this.lastOffsetX = offsetX;
        this.lastOffsetY = offsetY;
    }

    /**
     * Calcola l'offset della camera sull'asse Y per mantenere il player al centro,
     * con limiti ai bordi della mappa.
     */
    private double calculateCameraOffsetY(double paneHeight) {
        double renderedMapHeight = mapHeightInTiles * RENDERED_TILE_SIZE;
        double renderedPlayerY = playerY; // already in pixels

        double offsetY = paneHeight / 2 - renderedPlayerY;
        offsetY = Math.max(offsetY, paneHeight - renderedMapHeight);
        offsetY = Math.min(offsetY, 0);

        return offsetY;
    }

    private double calculateCameraOffsetX(double paneWidth) {
        double renderedMapWidth = mapWidthInTiles * RENDERED_TILE_SIZE;
        double renderedPlayerX = playerX; // already in pixels

        double offsetX = paneWidth / 2 - renderedPlayerX;
        offsetX = Math.max(offsetX, paneWidth - renderedMapWidth);
        offsetX = Math.min(offsetX, 0);

        return offsetX;
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

    private void drawMap(double offsetX, double offsetY) {
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

                        // 1. Calcolo SourceX e SourceY (rimane fisso, non dipende dalla scala)
                        int sourceX = (tileID % tilesetCols) * TILE_SIZE;
                        int sourceY = (tileID / tilesetCols) * TILE_SIZE;

                        // 2. Disegna la tessera ritagliata sul Canvas
                        gc.drawImage(
                                tileSetImage,
                                sourceX, sourceY, // Coordinate Sorgente (nel Tileset)
                                TILE_SIZE, TILE_SIZE, // Dimensione Sorgente (originale)

                                // Coordinate Destinazione (Scala applicata + Offset per centrare)
                                offsetX + x * this.RENDERED_TILE_SIZE,
                                offsetY + y * this.RENDERED_TILE_SIZE,

                                this.RENDERED_TILE_SIZE, this.RENDERED_TILE_SIZE // Dimensione Destinazione (scalata)
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

    public double getOffsetX() {
        return lastOffsetX;
    }

    public double getOffsetY() {
        return lastOffsetY;
    }

    /**
     * Aggiorna la posizione del player (in pixel della mappa originale).
     * Chiama requestLayout() per triggerare il re-rendering.
     */
    public void updatePlayerPosition(double x, double y) {
        this.playerX = x;
        this.playerY = y;
        requestLayout(); // Forza il layout e il re-rendering
    }
}
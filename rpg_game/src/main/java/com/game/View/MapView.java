package com.game.view;

import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;

// Importazioni dei modelli
import com.game.model.LayerData;
import com.game.model.TiledMapData;
// Rimuoviamo l'import di TileSetData perché non è più necessario qui.

public class MapView extends Pane {
    private Canvas canvas;
    private GraphicsContext gc;
    private Image tileSetImage;

    private TiledMapData mapData;

    // Variabili dinamiche
    private int TILE_SIZE = 0;
    private int mapWidthInTiles = 0;
    private int mapHeightInTiles = 0;
    private int tilesetCols = 0; // Contiene le colonne del tileset

    private double lastScale = 1.0;
    private double lastOffsetX = 0.0;
    private double lastOffsetY = 0.0;

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

        // 3. Setup del Canvas
        double canvasWidth = mapWidthInTiles * TILE_SIZE;
        double canvasHeight = mapHeightInTiles * TILE_SIZE;

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

        // 1. Calcola le dimensioni ideali in pixel della mappa originale
        final double originalMapWidth = mapWidthInTiles * TILE_SIZE;
        final double originalMapHeight = mapHeightInTiles * TILE_SIZE;

        // 2. Calcola il fattore di scala per adattarsi al Pane
        double scaleX = paneWidth / originalMapWidth;
        double scaleY = paneHeight / originalMapHeight;

        // CAMBIAMENTO CHIAVE: Usa Math.max per riempire completamente il Pane.
        // Questo garantisce che la mappa sia grande almeno quanto la vista in entrambe
        // le direzioni,
        // zoomando in modo proporzionale.
        double scale = Math.max(scaleX, scaleY);

        // 3. Imposta la dimensione del Canvas alla dimensione attuale del Pane
        if (canvas.getWidth() != paneWidth || canvas.getHeight() != paneHeight) {
            canvas.setWidth(paneWidth);
            canvas.setHeight(paneHeight);
        }

        // 4. Centra la mappa sul Canvas
        double renderedWidth = originalMapWidth * scale;
        double renderedHeight = originalMapHeight * scale;

        // Se la mappa è più grande della vista in una dimensione (ad esempio,
        // renderedWidth > paneWidth),
        // l'offsetX sarà negativo, centrando correttamente la porzione visibile.
        double offsetX = (paneWidth - renderedWidth) / 2;
        double offsetY = (paneHeight - renderedHeight) / 2;

        // 5. Ridisegna la mappa usando il fattore di scala e l'offset
        drawMap(scale, offsetX, offsetY);

        // Salva lo stato
        this.lastScale = scale;
        this.lastOffsetX = offsetX;
        this.lastOffsetY = offsetY;
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

    private void drawMap(double scale, double offsetX, double offsetY) {
        if (mapData == null || tileSetImage == null || tilesetCols == 0 || scale <= 0) {
            // Pulisci il Canvas se qualcosa è fallito (lo rende trasparente)
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            return;
        }

        // Pulisci il Canvas prima di disegnare (essenziale se lo ridisegni)
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        final double RENDERED_TILE_SIZE = TILE_SIZE * scale; // La nuova dimensione della tessera

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
                                offsetX + x * RENDERED_TILE_SIZE,
                                offsetY + y * RENDERED_TILE_SIZE,

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
        return TILE_SIZE;
    }

    public double getMapWidth() {
        return mapWidthInTiles * TILE_SIZE;
    }

    public double getMapHeight() {
        return mapHeightInTiles * TILE_SIZE;
    }

    public double getMapScale() {
        return lastScale;
    }

    public double getOffsetX() {
        return lastOffsetX;
    }

    public double getOffsetY() {
        return lastOffsetY;
    }
}
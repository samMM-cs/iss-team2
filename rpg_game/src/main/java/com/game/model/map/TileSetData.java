package com.game.model.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TileSetData {

    // Il numero di colonne nella griglia del Tileset PNG (potrebbe essere 0)
    @JsonProperty("columns")
    private int columns = 0;

    // Larghezza totale dell'immagine del Tileset in pixel (necessaria per il
    // calcolo)
    @JsonProperty("imagewidth")
    private int imageWidth;

    // Altezza totale dell'immagine del Tileset in pixel
    @JsonProperty("imageheight")
    private int imageHeight;

    // Percorso al file immagine del Tileset
    @JsonProperty("image")
    private String imagePath;

    // Costruttore vuoto (necessario per Jackson)
    public TileSetData() {
    }

    // --- Getter per accedere ai dati ---

    public int getColumns() {
        return columns;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public String getImagePath() {
        return imagePath;
    }
}
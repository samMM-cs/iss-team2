package com.game.model.map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TiledMapData {

    // Proprietà Generali della Mappa
    @JsonProperty("width")
    private int width; // Larghezza della mappa in numero di tessere

    @JsonProperty("height")
    private int height; // Altezza della mappa in numero di tessere

    @JsonProperty("tilewidth")
    private int tilewidth; // Larghezza di una singola tessera in pixel

    @JsonProperty("tileheight")
    private int tileheight; // Altezza di una singola tessera in pixel

    // Lista dei Livelli
    @JsonProperty("layers")
    private List<LayerData> layers; // Array di tutti i livelli

    // Costruttore vuoto (necessario per Jackson)
    public TiledMapData() {
    }

    // --- Getter per accedere ai dati nella tua MappaView ---

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public int getTileheight() {
        return tileheight;
    }

    public List<LayerData> getLayers() {
        return layers;
    }
}
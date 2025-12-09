package com.game.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LayerData {

    // Proprietà del Livello
    @JsonProperty("name")
    private String name; // Nome del livello (es. "Base", "Collisioni")

    @JsonProperty("type")
    private String type; // Tipo di livello (es. "tilelayer", "objectgroup")

    // L'array di numeri che rappresenta la mappa
    // I numeri sono gli ID delle tessere (ID Globali della Tessera o GID)
    @JsonProperty("data")
    private int[] data; 
    
    // Potresti voler includere anche "width" e "height" se il livello non copre l'intera mappa

    // Costruttore vuoto (necessario per Jackson)
    public LayerData() {
    }

    // --- Getter per accedere ai dati nella tua MappaView ---

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int[] getData() {
        return data;
    }
}
package com.game.Controller;


import javafx.event.ActionEvent;
import javafx.stage.*;

public class MainMenuController {
    private Stage stage;

    public MainMenuController(Stage stage) {
        this.stage = stage;
    }

    public void onNewGame(ActionEvent event) {
        //Carica schermata di gioco
        System.out.println("Avvio nuova partita");
    }
    
    public void resumeGame(ActionEvent event) {
        System.out.println("Seleziona lo slot");
    }
    public void onSettings(ActionEvent event) {
        //Carica la schermata settings
        System.out.println("Impostazioni");
    }

    public void onExit(ActionEvent event) {
        //Chiusura del gioco
        stage.close();
    }
}

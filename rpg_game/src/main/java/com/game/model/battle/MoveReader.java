package com.game.model.battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.InputStream;
import java.util.List;

public class MoveReader {
    public static List<Move> readMove(String path) {
        try (InputStream input = MoveReader.class.getResourceAsStream(path)) {
            ObjectMapper mapper = new ObjectMapper();

            if (input == null) {
                System.err.println("File non trovato");
                return List.of();// Resituisce una lista vuota immutabile
            }
            return mapper.readValue(input, new TypeReference<List<Move>>() {
            });
        } catch (Exception e) {
            System.err.println("Impossibile leggere JSON delle mosse");
            e.printStackTrace();
            return List.of();
        }
    }
}

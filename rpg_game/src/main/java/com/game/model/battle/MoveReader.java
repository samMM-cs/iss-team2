package com.game.model.battle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.InputStream;
import java.util.List;

public class MoveReader {
    public static List<Move> readMove(String path) {
        try {
            ObjectMapper mapper= new ObjectMapper();
            InputStream input= MoveReader.class
                .getClassLoader()
                .getResourceAsStream(path);
            if (input== null) {
                System.err.println("Stai ucciso");
                return null;
            }
            return mapper.readValue(input, new TypeReference<>() {});
        }
        catch (Exception e) {
            System.err.println("Impossibile leggere JSON delle mosse");
            e.printStackTrace();
            return null;
        }
    }
}

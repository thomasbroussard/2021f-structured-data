package fr.epita.etl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

public class Extractor {


    public JsonNode extract(){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = mapper.readTree(new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/json/file1.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonNode;

    }
}

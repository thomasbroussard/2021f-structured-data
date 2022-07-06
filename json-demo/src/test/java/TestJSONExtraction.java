import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epita.json.City;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class TestJSONExtraction {


    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<City> cities = mapper.readValue(
                new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/json/fr.json"),
                new TypeReference<List<City>>() {});

        double average = cities
                .stream()
                .mapToDouble(City::getPopulation)
                .average()
                .getAsDouble();

        System.out.println("avg is : " + average);
    }



}

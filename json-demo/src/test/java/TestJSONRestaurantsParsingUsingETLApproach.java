import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.epita.etl.Extractor;
import fr.epita.etl.Loader;
import fr.epita.etl.Transformer;

import java.io.IOException;
import java.net.URL;

public class TestJSONRestaurantsParsingUsingETLApproach {

    public static void main(String[] args) throws IOException {
        Extractor extractor = new Extractor();
        Transformer transformer = new Transformer();
        Loader loader = new Loader();

        JsonNode jsonRestaurantsList = extractor.extract();
        Double averageRating = transformer.transform(jsonRestaurantsList);
        loader.load(averageRating);

    }
}

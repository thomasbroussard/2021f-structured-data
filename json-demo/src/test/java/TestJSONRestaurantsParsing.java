import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TestJSONRestaurantsParsing {

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/json/file1.json"));

        System.out.println(jsonNode.size());

        Double average = 0.0;
        int validNumber = 0;
        for (int i = 0; i < jsonNode.size(); i++){
            JsonNode subRestaurantsList = jsonNode.get(i).get("restaurants");
            for (int j = 0; j < subRestaurantsList.size(); j++){
                String rating = subRestaurantsList.get(j).get("user_rating").get("aggregate_rating").textValue();
                Double aDouble = 0.0;
                try{
                   aDouble = Double.valueOf(rating);
                }catch (NumberFormatException nfe){
                    continue;
                }
                validNumber++;
                average += aDouble;

            }

        }

        System.out.println("average is :" + (average / validNumber));

    }
}

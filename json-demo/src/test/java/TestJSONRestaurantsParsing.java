import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class TestJSONRestaurantsParsing {

    public static void main(String[] args) throws IOException {

        //extract
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(new URL("https://thomas-broussard.fr/presentation/data-structuration-and-transportation/json/file1.json"));

        System.out.println(jsonNode.size());

        Double average = 0.0;
        int validNumber = 0;
        for (int i = 0; i < jsonNode.size(); i++){
            JsonNode subRestaurantsList = jsonNode.get(i).get("restaurants");
            if (subRestaurantsList == null){
                continue;
            }
            for (int j = 0; j < subRestaurantsList.size(); j++){
                JsonNode currentRestaurant = subRestaurantsList.get(j);
                if (currentRestaurant == null){
                    continue;
                }
                JsonNode currentRestaurantUserRating = currentRestaurant.get("restaurant").get("user_rating");
                if (currentRestaurantUserRating == null){
                    continue;
                }
                JsonNode aggregateRating = currentRestaurantUserRating.get("aggregate_rating");
                if (aggregateRating == null){
                    continue;
                }

                //transform
                String rating = aggregateRating.textValue();
                Double aDouble = 0.0;
                try{
                   aDouble = Double.valueOf(rating);
                }catch (Exception e){
                    continue;
                }
                validNumber++;
                average += aDouble;

            }

        }

        //load
        System.out.println("average is :" + (average / validNumber));

    }
}

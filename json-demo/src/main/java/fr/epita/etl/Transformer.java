package fr.epita.etl;

import com.fasterxml.jackson.databind.JsonNode;

public class Transformer {


    public Double transform(JsonNode jsonNode){


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
        return average/validNumber;
    }
}

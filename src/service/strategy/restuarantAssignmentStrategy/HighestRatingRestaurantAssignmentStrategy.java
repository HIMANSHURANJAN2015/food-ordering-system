package service.strategy.restuarantAssignmentStrategy;

import model.Restaurant;
import model.RestaurantMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighestRatingRestaurantAssignmentStrategy implements RestaurantAssignmentStrategy {

    public Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants){
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = new HashMap<>();

        Restaurant assignedRestaurant = null;
        Map<RestaurantMenuItem, Integer> outputItemIds = new HashMap<>();
        double highestRating = 0;
        for(Restaurant restaurant : restaurants){
            if(restaurant.getRating() < highestRating){
                continue;
            }
            Map<RestaurantMenuItem, Integer> tempOutputItemIds = new HashMap<>();
            for(RestaurantMenuItem restaurantMenuItem : restaurant.getMenuItems()){
                long correspondingMenuItemId = restaurantMenuItem.getMenuItem().getId();
                if(inputItemIds.containsKey(correspondingMenuItemId)){
                    int quantity = inputItemIds.get(correspondingMenuItemId);
                    tempOutputItemIds.put(restaurantMenuItem, quantity);
                }
            }
            assignedRestaurant = restaurant;
            outputItemIds = tempOutputItemIds;
            highestRating = restaurant.getRating();
            System.out.println("Rating for Res "+restaurant.getName()+" is= " + restaurant.getRating());
        }
        if(assignedRestaurant != null) {
            assignedRestaurantsMap.put(assignedRestaurant, outputItemIds);
        }
        return assignedRestaurantsMap;
    }
}

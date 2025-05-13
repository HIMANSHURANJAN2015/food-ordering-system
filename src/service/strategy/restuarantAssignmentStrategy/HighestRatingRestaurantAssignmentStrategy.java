package service.strategy.restuarantAssignmentStrategy;

import model.Restaurant;
import model.RestaurantMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighestRatingRestaurantAssignmentStrategy implements RestaurantAssignmentStrategy {

    public Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> orderedMenuItems, List<Restaurant> restaurants){
        return new HashMap<>();
    }
}

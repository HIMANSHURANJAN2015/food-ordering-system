package service.strategy.restuarantAssignmentStrategy;

import model.Restaurant;
import model.RestaurantMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RestaurantAssignmentStrategy {
    Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> orderedMenuItems, List<Restaurant> restaurants);
}

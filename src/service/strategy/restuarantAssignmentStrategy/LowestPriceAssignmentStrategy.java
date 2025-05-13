package service.strategy.restuarantAssignmentStrategy;

import model.Restaurant;
import model.RestaurantMenuItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowestPriceAssignmentStrategy implements RestaurantAssignmentStrategy {

    public Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants){
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = new HashMap<>();
        Restaurant assignedRestaurant = null;
        Map<RestaurantMenuItem, Integer> orderedRestaurantMenuItems = new HashMap<>();
        double minCost = Integer.MAX_VALUE;
        for(Restaurant restaurant : restaurants){
            //Calculating cost to process order by this restaurant
            double tempCost = 0;
            Map<RestaurantMenuItem, Integer> tempOrderedRestaurantMenuItems = new HashMap<>();
            for(RestaurantMenuItem restaurantMenuItem : restaurant.getMenuItems()){
                long correspondingMenuItemId = restaurantMenuItem.getMenuItem().getId();
                if(inputItemIds.containsKey(correspondingMenuItemId)){
                    int quantity = inputItemIds.get(correspondingMenuItemId);
                    double price = restaurantMenuItem.getPrice();
                    tempCost += (quantity * price);
                    tempOrderedRestaurantMenuItems.put(restaurantMenuItem, quantity);
                }
            }
            System.out.println("Cost for Res "+restaurant.getName()+" is= " + tempCost);
            if(tempCost < minCost){
                minCost = tempCost;
                assignedRestaurant = restaurant;
                orderedRestaurantMenuItems = tempOrderedRestaurantMenuItems;
            }
        }
        if(assignedRestaurant != null) {
            assignedRestaurantsMap.put(assignedRestaurant, orderedRestaurantMenuItems);
        }
        return assignedRestaurantsMap;
    }
}
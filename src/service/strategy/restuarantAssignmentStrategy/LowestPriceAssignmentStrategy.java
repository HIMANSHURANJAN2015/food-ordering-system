package service.strategy.restuarantAssignmentStrategy;

import exception.RestaurantNotFoundException;
import model.Restaurant;
import model.RestaurantMenuItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LowestPriceAssignmentStrategy implements RestaurantAssignmentStrategy {

    public Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants, boolean orderSplitAllowed){
        //checking if possible to get all items form single restaurants
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = this.assignOrderFromSingleRestaurant(inputItemIds, restaurants);
        if(assignedRestaurantsMap.size() > 0){
            return assignedRestaurantsMap;
        }
        //Since not possible from single restaurant, so checking from multiple restaurants, to keep the overall cost minimum
        if(orderSplitAllowed) {
            assignedRestaurantsMap = this.assignOrderFromMultipleRestaurant(inputItemIds, restaurants);
        }
        return assignedRestaurantsMap;
    }

    private Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrderFromSingleRestaurant(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants) {
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
            if(tempCost < minCost && tempOrderedRestaurantMenuItems.size() == inputItemIds.size()){
                //second condition ensures that all items are from single restaurant
                System.out.println("Cost for Res "+restaurant.getName()+" is= " + tempCost);
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

    private Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrderFromMultipleRestaurant(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants) {
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = new HashMap<>();
        for(long inputItemId : inputItemIds.keySet()){
            //Checking which restaurant serves this item at lowest price
            double minPrice = Integer.MAX_VALUE;
            Restaurant minPriceRestaurant = null;
            RestaurantMenuItem minPriceRestaurantMenuItem = null;


            for(Restaurant restaurant : restaurants) {
                //System.out.println("Checking for item: "+inputItemId+ " in restaurant: "+restaurant.getName());
                for(RestaurantMenuItem restaurantMenuItem : restaurant.getMenuItems()){
                    long correspondingMenuItemId = restaurantMenuItem.getMenuItem().getId();
                    if(correspondingMenuItemId == inputItemId && restaurantMenuItem.getPrice() < minPrice){
                        minPrice = restaurantMenuItem.getPrice();
                        minPriceRestaurantMenuItem = restaurantMenuItem;
                        minPriceRestaurant = restaurant;
                    }
                }
            }
            //System.out.println("Min price for item: "+ inputItemId + "is Rs"+ minPrice+" at restaurant"+minPriceRestaurant.getName());
            if(minPriceRestaurant == null){
                throw new RestaurantNotFoundException("Order item+"+inputItemId+" is not serviceable by any restaurant");
            }
            Map<RestaurantMenuItem, Integer> menuItemMap = assignedRestaurantsMap.getOrDefault(minPriceRestaurant, new HashMap<>());
            menuItemMap.put(minPriceRestaurantMenuItem, inputItemIds.get(inputItemId));
            assignedRestaurantsMap.put(minPriceRestaurant, menuItemMap);
        }
        return assignedRestaurantsMap;
    }
}
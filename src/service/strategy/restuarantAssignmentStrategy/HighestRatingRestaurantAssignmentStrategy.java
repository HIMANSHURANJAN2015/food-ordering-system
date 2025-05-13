package service.strategy.restuarantAssignmentStrategy;

import exception.RestaurantNotFoundException;
import model.Restaurant;
import model.RestaurantMenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HighestRatingRestaurantAssignmentStrategy implements RestaurantAssignmentStrategy {

    public Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrder(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants, boolean orderSplitAllowed) {
        //checking if possible to get all items form single restaurants
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = this.assignOrderFromSingleRestaurant(inputItemIds, restaurants);
        if (assignedRestaurantsMap.size() > 0) {
            return assignedRestaurantsMap;
        }
        //Since not possible from single restaurant, so checking from multiple restaurants, ensure each item is from restaurant with best rating
        if (orderSplitAllowed) {
            assignedRestaurantsMap = this.assignOrderFromMultipleRestaurant(inputItemIds, restaurants);
        }
        return assignedRestaurantsMap;
    }

    private Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrderFromSingleRestaurant(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants) {
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = new HashMap<>();

        Restaurant assignedRestaurant = null;
        Map<RestaurantMenuItem, Integer> outputItemIds = new HashMap<>();
        double highestRating = 0;
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getRating() < highestRating) {
                continue;
            }
            Map<RestaurantMenuItem, Integer> tempOutputItemIds = new HashMap<>();
            for (RestaurantMenuItem restaurantMenuItem : restaurant.getMenuItems()) {
                long correspondingMenuItemId = restaurantMenuItem.getMenuItem().getId();
                if (inputItemIds.containsKey(correspondingMenuItemId)) {
                    int quantity = inputItemIds.get(correspondingMenuItemId);
                    tempOutputItemIds.put(restaurantMenuItem, quantity);
                }
            }
            if(tempOutputItemIds.size() < inputItemIds.size()) {
                //This condition ensures that all items are from single restaurant
                continue;
            }
            assignedRestaurant = restaurant;
            outputItemIds = tempOutputItemIds;
            highestRating = restaurant.getRating();
            System.out.println("Rating for Res " + restaurant.getName() + " is= " + restaurant.getRating());
        }
        if (assignedRestaurant != null) {
            assignedRestaurantsMap.put(assignedRestaurant, outputItemIds);
        }
        return assignedRestaurantsMap;
    }

    private Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignOrderFromMultipleRestaurant(HashMap<Long, Integer> inputItemIds, List<Restaurant> restaurants) {
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = new HashMap<>();
        for(long inputItemId : inputItemIds.keySet()){
            //Checking the higest rating restaurant which serves this item
            double maxRating = 0;
            Restaurant bestRatingRestaurant = null;
            RestaurantMenuItem bestRatingRestaurantMenuItem = null;

            for(Restaurant restaurant : restaurants) {
                //System.out.println("Checking for item: "+inputItemId+ " in restaurant: "+restaurant.getName());
                for(RestaurantMenuItem restaurantMenuItem : restaurant.getMenuItems()){
                    long correspondingMenuItemId = restaurantMenuItem.getMenuItem().getId();
                    if(correspondingMenuItemId == inputItemId && restaurant.getRating() > maxRating){
                        maxRating = restaurantMenuItem.getPrice();
                        bestRatingRestaurant = restaurant;
                        bestRatingRestaurantMenuItem = restaurantMenuItem;
                    }
                }
            }
            //System.out.println("Min price for item: "+ inputItemId + "is Rs"+ minPrice+" at restaurant"+minPriceRestaurant.getName());
            if(bestRatingRestaurant == null){
                throw new RestaurantNotFoundException("Order item+"+inputItemId+" is not serviceable by any restaurant");
            }
            Map<RestaurantMenuItem, Integer> menuItemMap = assignedRestaurantsMap.getOrDefault(bestRatingRestaurant, new HashMap<>());
            menuItemMap.put(bestRatingRestaurantMenuItem, inputItemIds.get(inputItemId));
            assignedRestaurantsMap.put(bestRatingRestaurant, menuItemMap);
        }
        return assignedRestaurantsMap;
    }
}
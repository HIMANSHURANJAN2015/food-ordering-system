package repository;

import model.Restaurant;
import model.RestaurantMenuItem;
import model.constant.RestaurantStatus;
import java.util.*;

public class RestaurantRepository {

    private Map<Long, Restaurant> restaurantMap = new HashMap<>();
    private static long id = 1;


    public Restaurant save(Restaurant restaurant) {
        if(restaurant.getId() == 0) {
            restaurant.setId(id++);
        }
        restaurantMap.put(restaurant.getId(), restaurant);
        return restaurant;
    }

    public Optional<Restaurant> findById(long id) {
        return Optional.ofNullable(restaurantMap.get(id));
    }

    //public List<Restaurant> findAllRestaurantsByAvailabilityAndMenuItems(HashSet<Long> menuItemIds) {
    public List<Restaurant> findAllRestaurantsByAvailabilityAndMenuItems(HashSet<Long> inputItemIds, boolean orderSplitAllowed) {
        List<Restaurant> filteredRestaurants = new ArrayList<>();
        for(long restaurantId : restaurantMap.keySet()) {
            Restaurant restaurant = restaurantMap.get(restaurantId);
            if(restaurant.getRestaurantStatus() != RestaurantStatus.AVAILABLE) {
                continue;
            }
            //Now checking what all items this restaurant can service
            List<RestaurantMenuItem> outputItemIds = new ArrayList<>();
            for(RestaurantMenuItem menuItem : restaurant.getMenuItems()) {
                if(inputItemIds.contains(menuItem.getMenuItem().getId())) {
                    outputItemIds.add(menuItem);
                }
            }
            if(!orderSplitAllowed && inputItemIds.size() == outputItemIds.size()) {
                //since order split is not alowed, hence all items must be present in the restaurant
                filteredRestaurants.add(restaurant);
            } else if(outputItemIds.size() > 0){
                //means this restaurant serves atleaat 1 of the order, so can be helpful if we are splittign order
                filteredRestaurants.add(restaurant);
            }
        }
        return filteredRestaurants;
    }
}

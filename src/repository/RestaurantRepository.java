package repository;

import model.Restaurant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}

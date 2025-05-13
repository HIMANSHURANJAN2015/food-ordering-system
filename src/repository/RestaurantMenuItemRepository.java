package repository;

import model.RestaurantMenuItem;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RestaurantMenuItemRepository {
    private Map<Long, RestaurantMenuItem> restaurantMenuItemMap = new HashMap<>();
    private static long id =1;

    public RestaurantMenuItem save(RestaurantMenuItem restaurantMenuItem) {
        if(restaurantMenuItem.getId() == 0) {
            restaurantMenuItem.setId(id++);
        }
        restaurantMenuItemMap.put(restaurantMenuItem.getId(), restaurantMenuItem);
        return restaurantMenuItem;
    }

    public Optional<RestaurantMenuItem> findById(long id) {
        return Optional.ofNullable(restaurantMenuItemMap.get(id));
    }
}

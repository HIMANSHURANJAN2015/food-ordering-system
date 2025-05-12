package service;

import exception.InvalidRatingException;
import exception.RestaurantNotFoundException;
import repository.RestaurantRepository;
import model.Restaurant;
import model.MenuItem;
import utils.RestaurantUtils;
import java.util.List;
import java.util.Optional;

public class RestaurantService {
    private RestaurantRepository restuarantRepo;

    public RestaurantService(RestaurantRepository r) {
        this.restuarantRepo = r;
    }

    public Restaurant addRestaurant(String name, String address, String phone, List<MenuItem> menu, int maxNumberOfOrders, double rating) {
        //Basic validations done at controller. Here business logic validations are done
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        restaurant.setMenu(menu);
        if(rating > 5 || rating < 0) {
            throw new InvalidRatingException("Rating must be between 0 and 5");
        }
        restaurant.setRating(rating);
        restaurant.setMaxNoOfOrders(maxNumberOfOrders);
        return restuarantRepo.save(restaurant);
    }

    public void printRestaurant(long restaurantId) {
        Optional<Restaurant> restaurantOpt = restuarantRepo.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant with id = "+ restaurantId+" not found");
        }
        RestaurantUtils.print(restaurantOpt.get());
    }
}

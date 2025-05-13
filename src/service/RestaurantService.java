package service;

import exception.InvalidRatingException;
import exception.RestaurantNotFoundException;
import model.Order;
import model.RestaurantMenuItem;
import model.constant.RestaurantStatus;
import repository.RestaurantRepository;
import model.Restaurant;
import utils.RestaurantUtils;

import java.util.*;

public class RestaurantService {
    private RestaurantRepository restuarantRepo;
    private RestaurantMenuItemService restaurantMenuItemService;

    public RestaurantService(RestaurantRepository r, RestaurantMenuItemService s) {
        this.restuarantRepo = r;
        this.restaurantMenuItemService = s;
    }

    public Restaurant addRestaurant(String name, String address, String phone, Map<Long, Double> menuItemIdWithPrices, int maxNumberOfOrders, double rating) {
        //Basic validations done at controller. Here business logic validations are done

        //Creating restaurant object
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setPhone(phone);
        if(rating > 5 || rating < 0) {
            throw new InvalidRatingException("Rating must be between 0 and 5");
        }
        restaurant.setRating(rating);
        restaurant.setMaxNoOfOrders(maxNumberOfOrders);
        restaurant.setMenuItems(new ArrayList<RestaurantMenuItem>());
        restaurant.setRestaurantStatus(RestaurantStatus.AVAILABLE);
        restaurant.setProcessingOrders(new ArrayList<Order>());
        restaurant = restuarantRepo.save(restaurant);

        //Creating RestaurantMenu Item to store restaurant wise prices
        List<RestaurantMenuItem> menuItems = restaurant.getMenuItems();
        for(Map.Entry<Long, Double> entry : menuItemIdWithPrices.entrySet()) {
            long menuItemId = entry.getKey();
            double price = entry.getValue();
            restaurantMenuItemService.addRestaurantMenuItem(restaurant.getId(), menuItemId, price);
        }

        return restaurant;
    }

    public void printRestaurant(long restaurantId) {
        Optional<Restaurant> restaurantOpt = restuarantRepo.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant with id = "+ restaurantId+" not found");
        }
        RestaurantUtils.print(restaurantOpt.get());
    }

    public List<Restaurant> findAllRestaurantsByAvailabilityAndMenuItems(HashSet<Long> menuItemIds) {
        return restuarantRepo.findAllRestaurantsByAvailabilityAndMenuItems(menuItemIds);
    }

    public void orderAssigned(Restaurant restaurant, Order order) {
        List<Order> underProcessingOrders = restaurant.getProcessingOrders();
        underProcessingOrders.add(order);
        restaurant.setProcessingOrders(underProcessingOrders);
        if(underProcessingOrders.size() == restaurant.getMaxNoOfOrders()) {
            System.out.println("Restaurant + "+restaurant.getName() + " reached full capacity");
            restaurant.setRestaurantStatus(RestaurantStatus.FULLY_BOOKED);
        }
        restuarantRepo.save(restaurant);
    }

    public void orderCompleted(Restaurant restaurant, Order order) {
        List<Order> underProcessingOrders = restaurant.getProcessingOrders();
        underProcessingOrders.remove(order);
        restaurant.setProcessingOrders(underProcessingOrders);
        if(underProcessingOrders.size() < restaurant.getMaxNoOfOrders()) {
            System.out.println("Restaurant + "+restaurant.getName() + " is back");
            restaurant.setRestaurantStatus(RestaurantStatus.AVAILABLE);
        }
        restuarantRepo.save(restaurant);
    }
}
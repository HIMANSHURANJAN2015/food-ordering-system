package service;

import exception.MenuItemNotFoundException;
import exception.RestaurantNotFoundException;
import exception.UnauthorisedAccessException;
import model.MenuItem;
import model.Restaurant;
import model.RestaurantMenuItem;
import repository.MenuItemRepository;
import repository.RestaurantMenuItemRepository;
import repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;

public class RestaurantMenuItemService {
    private RestaurantMenuItemRepository restaurantMenuItemRepository;
    private RestaurantRepository restaurantRepository;
    private MenuItemRepository menuItemRepository;

    public RestaurantMenuItemService(RestaurantMenuItemRepository restaurantMenuItemRepository, RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantMenuItemRepository = restaurantMenuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public RestaurantMenuItem addRestaurantMenuItem(long restaurantId, long menuItemId, double price) {
        //Fetching restaurant object
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        Restaurant restaurant = restaurantOpt.get();

        //Fetching Menu Item object
        Optional<MenuItem> menuItemOpt = menuItemRepository.findById(menuItemId);
        if(menuItemOpt.isEmpty()) {
            throw new MenuItemNotFoundException("Menu item not found");
        }
        MenuItem menuItem = menuItemOpt.get();

        //Checking if price is >0
        if(price <=0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }

        //Creating RestaurantMenuItem object
        RestaurantMenuItem restaurantMenuItem = new RestaurantMenuItem();
        restaurantMenuItem.setRestaurant(restaurant);
        restaurantMenuItem.setMenuItem(menuItem);
        restaurantMenuItem.setPrice(price);
        restaurantMenuItem = restaurantMenuItemRepository.save(restaurantMenuItem);

        //Now updating the restaurant menu attribute.
        /*
        Whenever a new Restaurant menu item is added. this attribute needs to be updated also
         */
        List<RestaurantMenuItem> menu = restaurant.getMenuItems();
        menu.add(restaurantMenuItem);
        restaurant.setMenuItems(menu);

        return restaurantMenuItem;
    }

    public RestaurantMenuItem updateRestaurantMenuItem(long restaurantId, long restaurantMenuItemId, double price) {
        //Fetching restaurantId
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }

        //Fetching restaurantMenuItem
        Optional<RestaurantMenuItem> restaurantMenuItemOpt = restaurantMenuItemRepository.findById(restaurantMenuItemId);
        if(restaurantMenuItemOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant MenuItem not found");
        }
        RestaurantMenuItem restaurantMenuItem = restaurantMenuItemOpt.get();


        //Checking if this item belong to this restaurant
        if(restaurantMenuItem.getRestaurant().getId() != restaurantId) {
            throw new UnauthorisedAccessException("This menu item does nto belong to this restaurant");
        }

        restaurantMenuItem.setPrice(price);
        return restaurantMenuItemRepository.save(restaurantMenuItem);
    }
}
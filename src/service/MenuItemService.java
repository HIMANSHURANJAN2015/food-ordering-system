package service;

import exception.MenuItemNotFoundException;
import exception.RestaurantNotFoundException;
import exception.UnauthorisedAccessException;
import model.MenuItem;
import model.Restaurant;
import repository.MenuItemRepository;
import repository.RestaurantRepository;
import java.util.List;
import java.util.Optional;

public class MenuItemService {
    private MenuItemRepository menuItemRepository;
    private RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuItem addMenuItem(long restaurantId, String name, double price, String description) {
        //Check restaurant and fetch Restaurant object
        Optional<Restaurant> restaurantOpt = this.restaurantRepository.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        Restaurant restaurant = restaurantOpt.get();

        //Creating menuItem Object
        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        menuItem.setDescription(description);
        menuItem.setRestaurant(restaurant);
        menuItem = menuItemRepository.save(menuItem);

        //Updating Restaurant object
        List<MenuItem> menu = restaurant.getMenu();
        menu.add(menuItem);
        restaurant.setMenu(menu);
        this.restaurantRepository.save(restaurant);

        return menuItem;
    }

    public MenuItem updateMenuItem(long restaurantId, long menuItemId, String name, double price, String description) {
        //Check restaurant and fetch Restaurant object
        Optional<Restaurant> restaurantOpt = this.restaurantRepository.findById(restaurantId);
        if(restaurantOpt.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        Restaurant restaurant = restaurantOpt.get();

        //Checking is this item belong to this restaurant
        Optional<MenuItem> menuItemOpt = this.menuItemRepository.findById(menuItemId);
        if(menuItemOpt.isEmpty()) {
            throw new MenuItemNotFoundException("MenuItem not found");
        }
        MenuItem menuItem = menuItemOpt.get();
        if(menuItem.getRestaurant().getId() != restaurantId) {
            throw new UnauthorisedAccessException("This item cannot does not belong to this restaurant");
        }

        //Updating only those things which are passed
        if(!name.isEmpty()) {
            menuItem.setName(name);
        }
        if(!description.isEmpty()) {
            menuItem.setDescription(description);
        }
        if(menuItem.getPrice() != price) {
            menuItem.setPrice(price);
        }
        menuItem = menuItemRepository.save(menuItem);

        return menuItem;
        //No need to update menu attribute of Restaurant since its just updation of menu item object.
    }
}

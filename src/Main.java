import controller.CustomerController;
import controller.RestaurantController;
import controller.RestaurantMenuItemController;
import model.MenuItem;
import model.Restaurant;
import model.RestaurantMenuItem;
import repository.*;
import service.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to My Food Ordering System");

        //Creating all objects of repositories, service and pass the same everywhere.
        //Kind of mimicking SpringBeans
        CustomerRepository customerRepository = new CustomerRepository();
        MenuItemRepository menuItemRepository = new MenuItemRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        RestaurantMenuItemRepository restaurantMenuItemRepository = new RestaurantMenuItemRepository();
        OrderRepository orderRepository = new OrderRepository();


        CustomerService customerService = new CustomerService(customerRepository);
        MenuItemService menuItemService = new MenuItemService(menuItemRepository);
        RestaurantMenuItemService restaurantMenuItemService = new RestaurantMenuItemService(restaurantMenuItemRepository, restaurantRepository, menuItemRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository, restaurantMenuItemService);
        OrderService orderService = new OrderService(orderRepository, customerRepository);



        CustomerController customerController = new CustomerController(customerService);
        RestaurantMenuItemController restaurantMenuItemController = new RestaurantMenuItemController(restaurantMenuItemService, menuItemService);
        RestaurantController restaurantController = new RestaurantController(restaurantService, restaurantMenuItemController);



        Scanner scanner = new Scanner(System.in);
        try {
            int choice = 1;
            while (choice != 0) {
                System.out.println("Enter the choice code \n " +
                        "1 = Register a new Restaurant(with all details) \n " +
                        "2 = Auto-Register a new restaurant(with dummy data) \n " +
                        "3 = Print a restaurant \n " +
                        "4 = Add menu item \n " +
                        "5 = Update menu item \n " +
                        "6 = Add new customer \n " +
                        "7 = Get all menu items \n " +
                        "0 = Exit");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        restaurantController.addRestaurant();
                        break;
                    case 2:
                        loadRestaurantDetails(restaurantService, restaurantMenuItemService, menuItemService);
                        break;
                    case 3:
                        restaurantController.printRestaurant();
                        break;
                    case 4:
                        restaurantMenuItemController.addRestaurantMenuItem();
                        break;
                    case 5:
                        restaurantMenuItemController.updateMenuItem();
                        break;
                    case 6:
                        customerController.addCustomer();
                        break;
                    case 7:
                        restaurantMenuItemController.getAllMenuItemsAcrossRestaurant();
                        break;
                    case 0:
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + choice);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong: "+e.getMessage());
        }
    }

    private static void loadRestaurantDetails(RestaurantService restaurantService, RestaurantMenuItemService restaurantMenuItemService, MenuItemService menuItemService) {
        //Adding restaurant-1
        Restaurant restaurant1 = restaurantService.addRestaurant("R1", "WEst Patel Nagar, New Delhi-110008","9876543213", new HashMap<>(), 5, 4.5);
        MenuItem menuItem1 = menuItemService.addMenuItem("Veg biryani", "Delicious marinated veg biryani with raita");
        RestaurantMenuItem restaurantMenuItem1 = restaurantMenuItemService.addRestaurantMenuItem(restaurant1.getId(), menuItem1.getId(), 100);
        MenuItem menuItem2 = menuItemService.addMenuItem("Chicken biryani", "Delicious marinated chicken biryani with raita");
        RestaurantMenuItem restaurantMenuItem2 = restaurantMenuItemService.addRestaurantMenuItem(restaurant1.getId(), menuItem2.getId(), 150);

        //Adding restaurant-2
        Restaurant restaurant2 = restaurantService.addRestaurant("R2", "BTM laypout, Bangalore-560085","9876543214", new HashMap<>(), 5, 4);
        MenuItem menuItem3 = menuItemService.addMenuItem("Idli", "Delicious rice idli");
        RestaurantMenuItem restaurantMenuItem3 = restaurantMenuItemService.addRestaurantMenuItem(restaurant2.getId(), menuItem3.getId(), 10);
        MenuItem menuItem4 = menuItemService.addMenuItem("Dosa", "Dosa with cocnout chutney and sambhar");
        RestaurantMenuItem restaurantMenuItem4 = restaurantMenuItemService.addRestaurantMenuItem(restaurant2.getId(), menuItem4.getId(), 50);
        RestaurantMenuItem restaurantMenuItem5 = restaurantMenuItemService.addRestaurantMenuItem(restaurant2.getId(), menuItem1.getId(), 80);
        RestaurantMenuItem restaurantMenuItem6 = restaurantMenuItemService.addRestaurantMenuItem(restaurant2.getId(), menuItem2.getId(), 175);

        //Adding restaurant-3
        Restaurant restaurant3 = restaurantService.addRestaurant("R3", "Banshankharee, Bangalore-560001","9876543218", new HashMap<>(), 1, 4.9);
        RestaurantMenuItem restaurantMenuItem7 = restaurantMenuItemService.addRestaurantMenuItem(restaurant3.getId(), menuItem3.getId(), 15);
        RestaurantMenuItem restaurantMenuItem8 = restaurantMenuItemService.addRestaurantMenuItem(restaurant3.getId(), menuItem4.getId(), 30);
        MenuItem menuItem5 = menuItemService.addMenuItem("Gobi manchurian", "Healthy chinese snacks");
        RestaurantMenuItem restaurantMenuItem9 = restaurantMenuItemService.addRestaurantMenuItem(restaurant3.getId(), menuItem5.getId(), 150);
        RestaurantMenuItem restaurantMenuItem10 = restaurantMenuItemService.addRestaurantMenuItem(restaurant3.getId(), menuItem2.getId(), 175);

       System.out.println("Restaurant data loaded successfully. Kindly use print options to see the details. Kindly note the restaurant ids:"+
                Arrays.toString(new Long[]{restaurant1.getId(), restaurant2.getId(), restaurant3.getId()}));
    }
}
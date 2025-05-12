import controller.CustomerController;
import controller.MenuItemController;
import controller.RestaurantController;
import model.MenuItem;
import model.Restaurant;
import repository.CustomerRepository;
import repository.MenuItemRepository;
import repository.RestaurantRepository;
import service.CustomerService;
import service.MenuItemService;
import service.RestaurantService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to My Food Ordering System");

        //Creating all objects of repositories, service and pass the same everywhere.
        //Kind of mimicking SpringBeans
        MenuItemRepository menuItemRepository = new MenuItemRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        CustomerRepository customerRepository = new CustomerRepository();

        MenuItemService menuItemService = new MenuItemService(menuItemRepository, restaurantRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        CustomerService customerService = new CustomerService(customerRepository);

        MenuItemController menuItemController = new MenuItemController(menuItemService);
        RestaurantController restaurantController = new RestaurantController(restaurantService, menuItemController);
        CustomerController customerController = new CustomerController(customerService);

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
                        "0 = Exit");
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        restaurantController.addRestaurant();
                        break;
                    case 2:
                        loadRestaurantDetails(restaurantService, menuItemService);
                        break;
                    case 3:
                        restaurantController.printRestaurant();
                        break;
                    case 4:
                        menuItemController.addMenuItem();
                        break;
                    case 5:
                        menuItemController.updateMenuItem();
                        break;
                    case 6:
                        customerController.addCustomer();
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

    private static void loadRestaurantDetails(RestaurantService restaurantService, MenuItemService menuItemService) {
        //Adding restaurant-1
        Restaurant restaurant1 = restaurantService.addRestaurant("Mitra da dhaba", "WEst Patel Nagar, New Delhi-110008","9876543213", new ArrayList<MenuItem>(), 5, 4.5);
        menuItemService.addMenuItem(1, "Veg biryani", 100, "Delicious marinated veg biryani with raita");
        menuItemService.addMenuItem(1, "Chicken biryani", 150, "Delicious marinated chicken biryani with raita");
        menuItemService.addMenuItem(1, "Jackfruit biryani", 100, "Delicious marinated jackfruit biryani with raita");
        menuItemService.addMenuItem(1, "Chicken 65 biryani", 210, "Delicious marinated biryani with chinese tadka");

        //Adding restaurant-2
        Restaurant restaurant2 = restaurantService.addRestaurant("Sagar Foods, ", "BTM Layout 2nd stager, Bangalore-560085","9876543214", new ArrayList<MenuItem>(), 5, 3.5);
        menuItemService.addMenuItem(2, "Idli", 40, "Idli with sambhar and chutney");
        menuItemService.addMenuItem(2, "Dosa", 70, "Masala dosa");
        menuItemService.addMenuItem(2, "Jackfruit biryani", 90, "Delicious marinated jackfruit biryani with raita");
        menuItemService.addMenuItem(2, "Chicken 65 biryani", 180, "Delicious marinated biryani with chinese tadka");

        //Adding restaurant-3
        Restaurant restaurant3 = restaurantService.addRestaurant("Chinese corner, ", "Banshankahree, Bangalore-560012","9876543215", new ArrayList<MenuItem>(), 1, 4.9);
        menuItemService.addMenuItem(3, "Honey chilly potato", 40, "Sweet and salrty potato snacks");
        menuItemService.addMenuItem(3, "French fries", 80, "quick bites with tomator ketchup");
        menuItemService.addMenuItem(3, "Veg hakka noodles", 90, "pure veg noodles and a bit spicy");
        menuItemService.addMenuItem(3, "Chcken noodles", 110, "Chicken noodles");

        System.out.println("Restaurant data loaded successfully. Kindly use print options to see the details. Kindly note the restaurant ids:"+
                Arrays.toString(new Long[]{restaurant1.getId(), restaurant2.getId(), restaurant3.getId()}));
    }
}
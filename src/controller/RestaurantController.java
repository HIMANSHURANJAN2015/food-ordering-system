package controller;

import model.Restaurant;
import service.RestaurantService;
import java.util.HashMap;
import java.util.Scanner;

public class RestaurantController {
    private RestaurantService restaurantService;
    private RestaurantMenuItemController restaurantMenuItemController;
    Scanner scanner;

    public RestaurantController(RestaurantService restaurantService, RestaurantMenuItemController restaurantMenuItemController) {
        this.restaurantService = restaurantService;
        this.restaurantMenuItemController = restaurantMenuItemController;
        this.scanner = new Scanner(System.in);
    }

    public void addRestaurant() {
        try {
            //Taking inputs for creating restaurant object
            System.out.println("Enter restaurant name: ");
            String name = scanner.nextLine();
            System.out.println("Enter restaurant address: ");
            String address = scanner.nextLine();
            System.out.println("Enter restaurant phone: ");
            String phone = scanner.nextLine();
            System.out.println("Enter number of restaurant items you want to add. Atleast 1 menu item must be added");
            int numberOfMenuItems = Integer.parseInt(scanner.nextLine());
            if(numberOfMenuItems < 1) {
                throw new IllegalArgumentException("Atleast 1 menu item must be added");
            }
            System.out.println("Enter maximum number of orders for this restaurant");
            int maximumNumberOfOrders = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter rating of this restaurant: ");
            double rating = Double.parseDouble(scanner.nextLine());
            Restaurant restaurant = restaurantService.addRestaurant(name, address, phone, new HashMap<>(), maximumNumberOfOrders, rating);
            System.out.println("Restaurant added. Kindly note the id:"+restaurant.getId());

            //Calling RestaurantMenuItem controller for adding menu items. Calling it n times
            for(int i=1; i<=numberOfMenuItems; i++) {
                System.out.println("Enter details of item-"+i);
                this.restaurantMenuItemController.addRestaurantMenuItem();
            }

            //Printing restaurant details
            System.out.println("Restaurant successfully registered. Id: " + restaurant.getId());
            this.restaurantService.printRestaurant(restaurant.getId());
        } catch(Exception e) {
            System.out.println("OOPS!! Failed to register restaurant. Error: "+e.getMessage());
        }
    }

    public void printRestaurant() {
        try {
            System.out.println("Enter restaurant id");
            long restaurantId = Long.parseLong(scanner.nextLine());
            this.restaurantService.printRestaurant(restaurantId);
        } catch(Exception e) {
            System.out.println("OOPS!! Failed to print restaurant. Error: "+e.getMessage());
        }
    }
}
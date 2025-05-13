package controller;

import model.MenuItem;
import model.RestaurantMenuItem;
import service.MenuItemService;
import service.RestaurantMenuItemService;
import java.util.Scanner;

public class RestaurantMenuItemController {
    private RestaurantMenuItemService restaurantMenuItemService;
    private MenuItemService menuItemService;
    private Scanner scanner;

    public RestaurantMenuItemController(RestaurantMenuItemService restaurantMenuItemService, MenuItemService menuItemService) {
        this.restaurantMenuItemService = restaurantMenuItemService;
        this.menuItemService = menuItemService;
        this.scanner = new Scanner(System.in);
    }

    public void addRestaurantMenuItem() {
        try {
            //Taking inputs for creating MenuItem object
            System.out.println("Enter item name: ");
            String itemName = scanner.nextLine();
            System.out.println("Enter item description: ");
            String description = scanner.nextLine();
            MenuItem menuItem = menuItemService.addMenuItem(itemName, description);

            //Taking inputs for creating RestaurantMenuItem object
            System.out.println("Enter Restaurant Id to which this menu item belong");
            long restaurantId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter item price: ");
            double price = Double.parseDouble(scanner.nextLine());
            RestaurantMenuItem restaurantMenuItem = restaurantMenuItemService.addRestaurantMenuItem(restaurantId, menuItem.getId(), price);

            System.out.println("Restaurant Menu item added successfully. Item-id: "+restaurantMenuItem.getId());

        } catch(Exception e) {
            System.out.println("OOPS!! Failed to add menu item Error: "+ e.getMessage());

        }
    }

    public void updateMenuItem(){
       //As of now Restaurant menu item only has price. So, updating that only.
        try {
            //Taking inputs for updation
            System.out.println("Enter restaurant id to which this menu item belong");
            long restaurantId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter restaurantMenuItem ID which one wants to update");
            long restaurantMenuItemId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter the new price");
            double price = Double.parseDouble(scanner.nextLine());

            RestaurantMenuItem restaurantMenuItem = restaurantMenuItemService.updateRestaurantMenuItem(restaurantId, restaurantMenuItemId, price);
            System.out.println("RestaurantMenuitem updated successfully. RestaurantMenuItem-id: "+restaurantMenuItem.getId());
        } catch(Exception e){
            System.out.println("OOPS!! Failed to update menu item Error: "+ e.getMessage());
        }
    }
}

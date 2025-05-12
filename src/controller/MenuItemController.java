package controller;

import model.MenuItem;
import service.MenuItemService;
import java.util.Scanner;

public class MenuItemController {
    private MenuItemService menuItemService;
    private Scanner scanner;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
        this.scanner = new Scanner(System.in);
    }

    public void addMenuItem(){
        //I take input from user via command-line and give output via print to console.
        try {
            System.out.println("Enter Restaurant Id to which this menu item belong");
            long restaurantId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter item name: ");
            String itemName = scanner.nextLine();
            System.out.println("Enter item price: ");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter item description: ");
            String description = scanner.nextLine();
            MenuItem item = this.menuItemService.addMenuItem(restaurantId, itemName, price, description);
            System.out.println("Menu item added successfully. Item-id: "+item.getId());
        } catch (Exception e) {
            System.out.println("OOPS!! Failed to add menu item Error: "+ e.getMessage());
        }
    }

    public void updateMenuItem(){
        try {
            System.out.println("Enter menuitem id which one wants to update");
            long menuItemId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter restaurant id to which this menu item belong");
            long restaurantId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter new item name: (Give empty if one doesn't want to update name)");
            String name = scanner.nextLine();
            System.out.println("Enter new item name: (Give 0 if one doesn't want to update price)");
            double price = Double.parseDouble(scanner.nextLine());
            System.out.println("Enter new item description: (Give empty if one doesn't want to update description)");
            String description = scanner.nextLine();
            MenuItem item = this.menuItemService.updateMenuItem(restaurantId, menuItemId, name, price, description);
            System.out.println("Menu item updated successfully. Item-id: "+item.getId());
        } catch(Exception e){
            System.out.println("OOPS!! Failed to update menu item Error: "+ e.getMessage());
        }
    }
}

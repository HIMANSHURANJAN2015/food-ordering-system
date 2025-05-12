package utils;

import model.MenuItem;
import model.Restaurant;
import java.util.List;

public class RestaurantUtils {

    public static void print(Restaurant restaurant) {
        String border = "|------------------------------------------------------------------------------|";
        String emptyLine = "|                                                                              |";

        System.out.println(border); // Top border

        System.out.println(emptyLine);
        System.out.printf("|   %-74s   |\n", "Here are the details of the Restaurant");
        System.out.println(emptyLine);
        System.out.println(border); // Divider border
        System.out.printf("|   %-74s   |\n", "Restaurant ID  : " + restaurant.getId());
        System.out.printf("|   %-74s   |\n", "Restaurant NAME: " + restaurant.getName());
        System.out.printf("|   %-74s   |\n", "Restaurant Address: " + restaurant.getAddress());
        System.out.printf("|   %-74s   |\n", "Restaurant Phone : " + restaurant.getPhone());
        System.out.printf("|   %-74s   |\n", "Restaurant rating(out of 5) : " + restaurant.getRating());
        System.out.printf("|   %-74s   |\n", "Maximum number of orders : " + restaurant.getMaxNoOfOrders());

        System.out.println(border); // Divider border

        System.out.printf("|   %-74s   |\n", "Current menu items:");
        System.out.println(emptyLine);
        List<MenuItem> menuItemList = restaurant.getMenu();
        for(MenuItem menuItem : menuItemList) {
            System.out.printf("|   %-74s   |\n", menuItem.getId() + "   " + menuItem.getName() + "   Rs " + menuItem.getPrice());
        }
        System.out.println(border); // Bottom border
    }
}
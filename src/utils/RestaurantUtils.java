package utils;

import model.MenuItem;
import model.Order;
import model.Restaurant;
import model.RestaurantMenuItem;

import java.util.List;

public class RestaurantUtils {
    private static String border = "|------------------------------------------------------------------------------|";
    private static String emptyLine = "|                                                                              |";

    public static void print(Restaurant restaurant) {
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
        System.out.printf("|   %-74s   |\n", "Restaurant status : " + restaurant.getRestaurantStatus());

        System.out.println(border); // Divider border

        System.out.printf("|   %-74s   |\n", "Current menu items:");
        System.out.println(emptyLine);
        List<RestaurantMenuItem> menuItemList = restaurant.getMenuItems();
        for(RestaurantMenuItem restaurantMenuItem : menuItemList) {
            System.out.printf("|   %-74s   |\n", restaurantMenuItem.getId() + "   " + restaurantMenuItem.getMenuItem().getName() + "   Rs " + restaurantMenuItem.getPrice());
        }
        System.out.println(border); // Divider border

        System.out.printf("|   %-74s   |\n", "Current Processing Orders:");
        System.out.println(emptyLine);
        for(Order order: restaurant.getProcessingOrders()) {
            System.out.printf("| %-4s | %-15s |  %-52s |\n",
                    order.getId(),
                    order.getCustomer().getName(),
                    order.orderItemsToString());
        }

        System.out.println(border); // Bottom border
    }

    public static void print(List<MenuItem> menuitems) {
        System.out.println(border); // Top border
        System.out.println(emptyLine);
        System.out.printf("|   %-74s   |\n", "Here are the Food menu list");
        System.out.println(emptyLine);
        System.out.println(border);
        for(MenuItem menuItem : menuitems) {
            System.out.printf("| %-4s | %-20s |  %-47s |\n",
                    menuItem.getId(),
                    menuItem.getName(),
                    menuItem.getDescription());
        }
        System.out.println(border);

    }

    public static void print(Order order) {
        System.out.println(border); // Top border
        System.out.println(emptyLine);
        System.out.printf("|   %-74s   |\n", "Here are the order details");
        System.out.println(emptyLine);
        System.out.println(border);
        System.out.printf("|   %-74s   |\n", "Order ID  : " + order.getId());
        System.out.printf("|   %-74s   |\n", "Customer NAME: " + order.getCustomer().getName());
        System.out.printf("|   %-74s   |\n", "Restaurant NAME: " + order.getRestaurant().getName());
        System.out.printf("|   %-74s   |\n", "Ordered Items: " + order.orderItemsToString());
        System.out.printf("|   %-74s   |\n", "Order Amount: Rs " + order.getAmount());
        System.out.printf("|   %-74s   |\n", "Restaurant Assignmentstratgey : " + order.getAssignmentType());
        System.out.printf("|   %-74s   |\n", "Order Status : " + order.getOrderStatus());
        System.out.println(border);//bottom border
    }
}
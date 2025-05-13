package controller;

import model.Order;
import service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private OrderService orderService;
    private Scanner scanner;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
        this.scanner = new Scanner(System.in);
    }

    public void placeOrder() {
        try {
            //Taking inputs for placing order
            System.out.println("Enter Customer ID");
            long customerId = Long.parseLong(scanner.nextLine());
            System.out.println("Enter number of items to order");
            int itemCount = Integer.parseInt(scanner.nextLine());
            HashMap<Long, Integer> orderedMenuItemsIds = new HashMap<>();
            for (int i = 1; i <= itemCount; i++) {
                System.out.println("Enter menuItem ID");
                long itemId = Long.parseLong(scanner.nextLine());
                System.out.println("Enter quantity");
                int quantity = Integer.parseInt(scanner.nextLine());
                orderedMenuItemsIds.put(itemId, quantity);
            }
            System.out.println("Enter Restaurant strategy type. Enter the correct single digit code: \n" +
                    "1. LOWEST_COST \n"
                    +"2. HIGHEST_RATING \n");
            int choice = Integer.parseInt(scanner.nextLine());
            String type = "";
            if(choice == 1) {
                type = "LOWEST_COST";
            } else if(choice == 2) {
                type = "HIGHEST_RATING";
            }
            System.out.println("Do you want to allow order split ot get the best offers? Enter the correct sinle digit code: \n "+
                    "1. Dont Allow order split \n"+
                    "2. Allow order split");
            choice = Integer.parseInt(scanner.nextLine());
            boolean orderSplitAllowed = false;
            if(choice == 2) {
                orderSplitAllowed = true;
            }

            //Calling order service
            List<Order> orders = orderService.placeOrder(customerId, orderedMenuItemsIds, type, orderSplitAllowed);

            //Printing the orders
            if(orders.size() > 1) {
                System.out.println("For your convenience, we have split the order into " + orders.size() + " orders");
            }
            for (Order order : orders) {
                System.out.println("Order placed successfully. Kindly note the id for future reference. ID: " + order.getId());
                orderService.printOrder(order.getId());
            }
        } catch(Exception e) {
            System.out.println("OOPSSS!! Failed to place an order. Error:"+e.getMessage());
        }
    }

    public void printOrder() {
        try {
            System.out.println("Enter order ID");
            long orderId = Long.parseLong(scanner.nextLine());
            orderService.printOrder(orderId);
        } catch(Exception e) {
            System.out.println("OOPSSS!! Failed to print order. Error:"+e.getMessage());
        }
    }

    public void completeOrder() {
        try {
            System.out.println("Enter order ID");
            long orderId = Long.parseLong(scanner.nextLine());
            orderService.completeOrder(orderId);
            System.out.println("Order completed successfully. Kindly note the id for future reference."+orderId);
        } catch(Exception e) {
            System.out.println("OOPSSS!! Failed to complete order. Error:"+e.getMessage());
        }
    }
}

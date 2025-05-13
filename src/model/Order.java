package model;

import model.constant.OrderStatus;
import model.constant.RestaurantAssignmentType;
import java.util.Map;

public class Order extends BaseModel {
    private Customer customer;
    private Restaurant restaurant;
    private Map<RestaurantMenuItem, Integer> orderedItems;
    private double amount;
    private OrderStatus orderStatus;
    private RestaurantAssignmentType assignmentType;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<RestaurantMenuItem, Integer> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Map<RestaurantMenuItem, Integer> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String orderItemsToString() {
        String res = "[";
        for(Map.Entry<RestaurantMenuItem, Integer> entry : orderedItems.entrySet()) {
            RestaurantMenuItem item = entry.getKey();
            String itemName = item.getMenuItem().getName();
            res+= itemName + ":"+entry.getValue();
            res += "  ";
        }
        res+="]";
        return res;
    }

    public RestaurantAssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(RestaurantAssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }
}

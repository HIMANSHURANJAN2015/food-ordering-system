package model;

import model.constant.OrderStatus;

import java.util.Map;

public class Order extends BaseModel {
    private Customer customer;
    private Map<RestaurantMenuItem, Integer> orderedItems;
    private OrderStatus orderStatus;
    private double amount;
    private Restaurant restaurant;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<RestaurantMenuItem, Integer> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Map<RestaurantMenuItem, Integer> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}

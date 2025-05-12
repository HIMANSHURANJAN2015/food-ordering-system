package model;

import model.constant.OrderStatus;

import java.util.Map;

public class Order {
    private Customer customer;
    private Map<MenuItem, Integer> orderedItems;
    private OrderStatus orderStatus;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Map<MenuItem, Integer> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(Map<MenuItem, Integer> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}

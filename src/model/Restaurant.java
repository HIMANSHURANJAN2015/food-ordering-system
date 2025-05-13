package model;

import model.constant.RestaurantStatus;

import java.util.List;

public class Restaurant extends BaseModel {
    private String name;
    private String address;
    private String phone;
    private double rating;//0-5
    private int maxNoOfOrders;
    private RestaurantStatus restaurantStatus;
    private List<RestaurantMenuItem> menuItems;
    private List<Order> processingOrders;
    // Restaurant:MenuItem = N:M with prices of items varying across restaurant so stored in RestaurantMapping mapping table

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMaxNoOfOrders() {
        return maxNoOfOrders;
    }

    public void setMaxNoOfOrders(int maxNoOfOrders) {
        this.maxNoOfOrders = maxNoOfOrders;
    }

    public RestaurantStatus getRestaurantStatus() {
        return restaurantStatus;
    }

    public void setRestaurantStatus(RestaurantStatus restaurantStatus) {
        this.restaurantStatus = restaurantStatus;
    }

    public List<RestaurantMenuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<RestaurantMenuItem> menu) {
        this.menuItems = menu;
    }

    public List<Order> getProcessingOrders() {
        return processingOrders;
    }

    public void setProcessingOrders(List<Order> processingOrders) {
        this.processingOrders = processingOrders;
    }
}

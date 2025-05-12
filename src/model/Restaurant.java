package model;

import java.util.List;

public class Restaurant extends BaseModel {
    private String name;
    private String address;
    private String phone;
    private List<MenuItem> menu;
    private double rating;//0-5
    private int maxNoOfOrders;

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

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
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
}

package controller;

import exception.UnauthorisedAccessException;
import model.Customer;
import service.CustomerService;

import java.util.Scanner;

public class CustomerController {
    private CustomerService customerService;
    private Scanner scanner;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
        this.scanner = new Scanner(System.in);
    }

    public void addCustomer() {
        try {
            System.out.print("Enter Customer name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Customer phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter Customer address: ");
            String address = scanner.nextLine();
            if(name.isEmpty() || phoneNumber.isEmpty()) {
                throw new IllegalArgumentException("Customer name or phone number is empty");
            }
            Customer customer = customerService.createCustomer(name, phoneNumber, address);
            System.out.println("Customer added successfully. Customer-id: "+customer.getId());
        } catch(Exception e){
            System.out.println("OOPS!! Failed to add new customer. Error: "+ e.getMessage());
        }
    }
}

package service;

import model.Customer;
import repository.CustomerRepository;

import java.util.ArrayList;

public class CustomerService {
    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(String name, String phone, String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setOrders(new ArrayList<>());
        return customerRepository.save(customer);
    }
}

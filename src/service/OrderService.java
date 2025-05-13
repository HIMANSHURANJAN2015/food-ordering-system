package service;

import exception.CustomerNotFoundException;
import model.Customer;
import model.MenuItem;
import model.Order;
import model.Restaurant;
import repository.CustomerRepository;
import repository.OrderRepository;

import java.util.*;

public class OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    public Order placeOrder(long customerId, HashMap<String, Integer> orderedItemsByName) {
        //Validating customer
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer customer = customerOpt.get();

        //Assigning order to a restaurant using selection strategies
        Restaurant restaurant = new Restaurant();
        Map<MenuItem, Integer> orderedItems = new HashMap<>();
        double amount = 0;

        //Creating Order object
        Order order = new Order();
        order.setCustomer(customer);
        //order.setOrderedItems(orderedItems);
        order.setAmount(amount);
        order.setRestaurant(restaurant);
        order = orderRepository.save(order);
        return order;
    }
}

/*
  dosa -2
  idli 3

  I need to fin all restaurant which can cook all items && isAvailable
  From it I will select restaurnt whihc has lowest cost or best rating

 */

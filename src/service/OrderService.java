package service;

import exception.CustomerNotFoundException;
import exception.NoAvailableRestaurantFoundException;
import exception.OrderNotFoundException;
import model.*;
import model.constant.OrderStatus;
import model.constant.RestaurantAssignmentType;
import repository.CustomerRepository;
import repository.OrderRepository;
import service.strategy.restuarantAssignmentStrategy.RestaurantAssignmentStrategy;
import service.strategy.restuarantAssignmentStrategy.RestaurantAssignmentStrategyFactory;
import utils.RestaurantUtils;

import java.util.*;

public class OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private RestaurantService restaurantService;
    private final RestaurantAssignmentType DEFAULT_RESTAURANT_ASSIGNMENT_TYPE = RestaurantAssignmentType.LOWEST_COST;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, RestaurantService restaurantService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantService = restaurantService;
    }

    public List<Order> placeOrder(long customerId, HashMap<Long, Integer> orderedMenuItemsId, String strategy, boolean orderSplitAllowed) {
        //Validating customerId and fetching customer object
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found");
        }
        Customer customer = customerOpt.get();

        //Calculating the strategy to use for this order
        RestaurantAssignmentType restaurantAssignmentType = DEFAULT_RESTAURANT_ASSIGNMENT_TYPE;
        try {
            restaurantAssignmentType = RestaurantAssignmentType.valueOf(strategy.toUpperCase()); // Case-insensitive conversion
        } catch (IllegalArgumentException e) {
            System.out.println("Warning: Invalid strategy type: " + strategy);
        }
        RestaurantAssignmentStrategy restaurantAssignmentStrategy = RestaurantAssignmentStrategyFactory.getRestaurantAssignmentStrategy(restaurantAssignmentType);


        //Assigning order to a restaurant using user defined selection strategies
        List<Restaurant> filteredRestaurants = restaurantService.findAllRestaurantsByAvailabilityAndMenuItems(new HashSet<>(orderedMenuItemsId.keySet()), orderSplitAllowed);
        Map<Restaurant, Map<RestaurantMenuItem, Integer>> assignedRestaurantsMap = restaurantAssignmentStrategy.assignOrder(orderedMenuItemsId,filteredRestaurants, orderSplitAllowed);
        if(assignedRestaurantsMap.isEmpty()) {
            throw new NoAvailableRestaurantFoundException("All our restaurants are currently busy. Kindly try after some time");
        }

        //Calculating amount and placing order
        List<Order> orders = new ArrayList<>();
        for(Restaurant restaurant : assignedRestaurantsMap.keySet()) {
            double amount = 0;
            Map<RestaurantMenuItem, Integer> orderedItemsMapFromThisRestaurant = assignedRestaurantsMap.get(restaurant);
            for(RestaurantMenuItem restaurantMenuItem : orderedItemsMapFromThisRestaurant.keySet()) {
                int quantity = orderedItemsMapFromThisRestaurant.get(restaurantMenuItem);
                amount += (quantity * restaurantMenuItem.getPrice());
            }
            Order order = new Order();
            order.setCustomer(customer);
            order.setRestaurant(restaurant);
            order.setOrderedItems(orderedItemsMapFromThisRestaurant);
            order.setAmount(amount);
            order.setAssignmentType(restaurantAssignmentType);
            order.setOrderStatus(OrderStatus.ACCEPTED);
            order = orderRepository.save(order);
            orders.add(order);

            //Updating status of Restaurant
            restaurantService.orderAssigned(restaurant, order);
        }
        return orders;
    }

    public void printOrder(long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if(orderOpt.isEmpty()) {
            throw new OrderNotFoundException("Order with id = "+ orderId+" not found");
        }
        RestaurantUtils.print(orderOpt.get());
    }

    public Order completeOrder(long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if(orderOpt.isEmpty()) {
            throw new OrderNotFoundException("Order with id = "+ orderId+" not found");
        }
        Order order = orderOpt.get();
        order.setOrderStatus(OrderStatus.COMPLETED);
        order = orderRepository.save(order);

        //Freeing space of restaurant
        restaurantService.orderCompleted(order.getRestaurant(), order);
        return order;
    }
}
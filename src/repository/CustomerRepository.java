package repository;

import model.Customer;
import model.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CustomerRepository {
    Map<Long, Customer> customerMap = new HashMap<Long, Customer>();
    private static long id = 1;

    public Customer save(Customer customer) {
        if(customer.getId()==0) {
            customer.setId(id++);
        }
        customerMap.put(customer.getId(), customer);
        return customer;
    }

    public Optional<Customer> findById(long id) {
        return Optional.ofNullable(customerMap.get(id));
    }
}

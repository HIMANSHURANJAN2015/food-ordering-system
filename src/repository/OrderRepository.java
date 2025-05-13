package repository;

import model.Order;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OrderRepository {
    Map<Long, Order> orderMap = new HashMap<Long, Order>();
    private static long id = 1;


    public Order save(Order order) {
        if(order.getId()==0) {
            order.setId(id++);
        }
        orderMap.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(long id) {
        return Optional.ofNullable(orderMap.get(id));
    }
}

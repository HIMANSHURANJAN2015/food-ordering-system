package service.strategy.restuarantAssignmentStrategy;

import model.Restaurant;
import java.util.Optional;

public interface RestaurantAssignmentStrategy {
    Optional<Restaurant> assignOrder();
}

package service.strategy.restuarantAssignmentStrategy;

import model.constant.RestaurantAssignmentType;

public class RestaurantAssignmentStrategyFactory {

    public static RestaurantAssignmentStrategy getRestaurantAssignmentStrategy(RestaurantAssignmentType type) {
        switch (type) {
            case LOWEST_COST:
                 return new LowestPriceAssignmentStrategy();
            case HIGHEST_RATING:
                return new HighestRatingRestaurantAssignmentStrategy();
            default:
                return new LowestPriceAssignmentStrategy();//Default strategy
        }
    }
}

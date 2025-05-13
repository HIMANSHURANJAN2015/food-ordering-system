package exception;

public class NoAvailableRestaurantFoundException extends RuntimeException {
    public NoAvailableRestaurantFoundException(String message) {
        super(message);
    }
}

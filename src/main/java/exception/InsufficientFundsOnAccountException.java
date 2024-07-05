package src.main.java.exception;

public class InsufficientFundsOnAccountException extends RuntimeException {
    public InsufficientFundsOnAccountException(String message) {
        super(message);
    }
}

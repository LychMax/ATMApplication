package src.main.java.exception;

public class InsufficientFundsOnATMException extends RuntimeException {
    public InsufficientFundsOnATMException(String message) {
        super(message);
    }
}

package src.main.java.exception;

public class PinAttemptsExceededException extends RuntimeException {
    public PinAttemptsExceededException(String message) {
        super(message);
    }
}

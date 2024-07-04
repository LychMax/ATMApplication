package src.main.java.exception;

public class CheckCardNumberException extends RuntimeException{
    public CheckCardNumberException(String message) {
        super(message);
    }
}

package src.main.java.exception;

public class ATMDataNotFoundException extends RuntimeException{
    public ATMDataNotFoundException() {
    }

    public ATMDataNotFoundException(String message){
        super(message);
    }
}

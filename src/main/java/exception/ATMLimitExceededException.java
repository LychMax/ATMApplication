package src.main.java.exception;

public class ATMLimitExceededException extends RuntimeException{
    public ATMLimitExceededException(String meesage){
        super(meesage);
    }
}

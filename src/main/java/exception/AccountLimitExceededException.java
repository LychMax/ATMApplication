package src.main.java.exception;

public class AccountLimitExceededException extends RuntimeException{
    public AccountLimitExceededException(String meesage){
        super(meesage);
    }
}

package SERVER.SERVER;

public class ValidationError extends RuntimeException{
    private int errorCode;
    public ValidationError(String message, int errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}

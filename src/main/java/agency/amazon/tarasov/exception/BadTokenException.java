package agency.amazon.tarasov.exception;

public class BadTokenException extends BasicException {
    public BadTokenException(Throwable cause){
        super("authorization failed", cause);
    }
}

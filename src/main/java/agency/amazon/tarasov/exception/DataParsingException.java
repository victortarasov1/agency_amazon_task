package agency.amazon.tarasov.exception;

public class DataParsingException extends BasicException {
    public DataParsingException(Throwable cause) {
        super("data parsion exception", cause);
    }
}

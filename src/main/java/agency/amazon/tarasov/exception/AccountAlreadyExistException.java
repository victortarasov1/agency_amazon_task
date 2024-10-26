package agency.amazon.tarasov.exception;

public class AccountAlreadyExistException extends BasicException {
    public AccountAlreadyExistException(String email) {
        super("account with email: " + email + " already exists");
    }
}

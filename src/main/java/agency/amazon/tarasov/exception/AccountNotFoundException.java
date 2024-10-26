package agency.amazon.tarasov.exception;

public class AccountNotFoundException extends BasicException {
    public AccountNotFoundException(String email) {
        super("account with email: " + email + " was not found!");
    }
}

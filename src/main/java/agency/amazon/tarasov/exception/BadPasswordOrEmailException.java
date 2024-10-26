package agency.amazon.tarasov.exception;

public class BadPasswordOrEmailException extends BasicException {
    public BadPasswordOrEmailException(String email) {
        super("bad password and/or email! " + email);
    }
}

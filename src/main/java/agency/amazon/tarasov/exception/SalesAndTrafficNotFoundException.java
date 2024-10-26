package agency.amazon.tarasov.exception;

public class SalesAndTrafficNotFoundException extends BasicException {
    public SalesAndTrafficNotFoundException(String id) {
        super("Sales and Traffic report with id: " + id + " was not found!");
    }
}

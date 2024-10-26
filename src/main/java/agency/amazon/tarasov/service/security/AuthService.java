package agency.amazon.tarasov.service.security;

import java.util.Map;

public interface AuthService {
    Map<String, String> authenticate(String username, String password);

    void authorizeIfTokenIsValid(String accessToken);
}

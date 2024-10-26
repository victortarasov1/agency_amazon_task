package agency.amazon.tarasov.service.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface TokenProvider {
    Map<String, String> generateTokens(UserDetails details);

    Map<String, String> regenerateTokensIfRefreshTokenIsValid(String refreshToken);
}

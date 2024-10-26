package agency.amazon.tarasov.service.security;

import agency.amazon.tarasov.config.SecurityConfigHolder;
import agency.amazon.tarasov.exception.BadTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static agency.amazon.tarasov.constant.Constants.*;

@Component
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {
    private final Algorithm algorithm;
    private final JWTVerifier verifier;
    private final UserDetailsService service;
    private final SecurityConfigHolder holder;


    @Override
    public Map<String, String> generateTokens(UserDetails details) {
        return Map.of(ACCESS_TOKEN, generateAccessToken(details),
                REFRESH_TOKEN, generateRefreshToken(details));
    }

    @Override
    public Map<String, String> regenerateTokensIfRefreshTokenIsValid(String refreshToken) {
        try {
            var decodedJWT = verifier.verify(refreshToken);
            var username = decodedJWT.getSubject();
            var user = service.loadUserByUsername(username);
            return Map.of(ACCESS_TOKEN, generateAccessToken(user));
        } catch (TokenExpiredException | JWTDecodeException | UsernameNotFoundException | NullPointerException ex) {
            throw new BadTokenException(ex);
        }
    }

    private String generateRefreshToken(UserDetails user) {
        var instant = Instant.now();
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(instant.plus(holder.getRefreshTokenTime(), ChronoUnit.MINUTES))
                .sign(algorithm);
    }

    private String generateAccessToken(UserDetails user) {
        var instant = Instant.now();
        return JWT.create().withSubject(user.getUsername())
                .withExpiresAt(instant.plus(holder.getAccessTokenTime(), ChronoUnit.MINUTES))
                .withClaim(ROLES_TOKEN_CLAIM, user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList()).sign(algorithm);
    }
}

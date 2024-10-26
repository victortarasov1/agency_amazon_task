package agency.amazon.tarasov.service.security;

import agency.amazon.tarasov.exception.BadPasswordOrEmailException;
import agency.amazon.tarasov.exception.BadTokenException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.JWTVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Map;

import static agency.amazon.tarasov.constant.Constants.ROLES_TOKEN_CLAIM;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenProvider provider;
    private final AuthenticationManager manager;
    private final JWTVerifier verifier;

    @Override
    public Map<String, String> authenticate(String username, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        var details = (UserDetails) getUserDetails(authenticationToken);
        return provider.generateTokens(details);
    }

    @Override
    public void authorizeIfTokenIsValid(String accessToken) {
        try {
            var decodedJWT = verifier.verify(accessToken);
            var username = decodedJWT.getSubject();
            var roles = decodedJWT.getClaim(ROLES_TOKEN_CLAIM).asList(String.class);
            var authorities = roles.stream().map(SimpleGrantedAuthority::new).toList();
            var authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (TokenExpiredException | JWTDecodeException | SignatureVerificationException | NullPointerException ex) {
            throw new BadTokenException(ex);
        }
    }

    private UserDetails getUserDetails(UsernamePasswordAuthenticationToken token) {
        try {
            return (UserDetails) manager.authenticate(token).getPrincipal();
        } catch (AuthenticationException ex) {
            throw new BadPasswordOrEmailException(token.getName());
        }
    }
}

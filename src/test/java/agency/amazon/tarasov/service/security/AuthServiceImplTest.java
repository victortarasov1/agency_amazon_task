package agency.amazon.tarasov.service.security;

import agency.amazon.tarasov.exception.BadPasswordOrEmailException;
import agency.amazon.tarasov.exception.BadTokenException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

import static agency.amazon.tarasov.model.Role.ROLE_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {
    private TokenProvider provider;
    private AuthenticationManager manager;
    private JWTVerifier verifier;
    private AuthService service;

    @BeforeEach
    public void setUp() {
        provider = mock(TokenProvider.class);
        manager = mock(AuthenticationManager.class);
        verifier = mock(JWTVerifier.class);
        service = new AuthServiceImpl(provider, manager, verifier);
    }

    @Test
    public void testAuthenticate() {
        var tokens = Map.of("access_token", "refresh_token");
        var authentication = mock(Authentication.class);

        when(provider.generateTokens(any(UserDetails.class))).thenReturn(tokens);
        when(manager.authenticate(any())).thenReturn(authentication);

        service.authenticate("username", "password");
    }

    @Test
    public void testAuthenticate_shouldThrowBadPasswordOrEmailException() {
        when(manager.authenticate(any())).thenThrow(BadCredentialsException.class);

        assertThatThrownBy(() -> service.authenticate("username", "password")).isInstanceOf(BadPasswordOrEmailException.class);

    }

    @Test
    public void test_AuthorizeIfValid() {
        var decodedJWT = mock(DecodedJWT.class);
        var claim = mock(Claim.class);

        when(verifier.verify(anyString())).thenReturn(decodedJWT);
        when(decodedJWT.getSubject()).thenReturn("username");
        when(decodedJWT.getClaim("roles")).thenReturn(claim);
        when(claim.asList(String.class))
                .thenReturn(List.of(ROLE_USER.toString()));
        when(verifier.verify("validToken")).thenReturn(decodedJWT);

        service.authorizeIfTokenIsValid("validToken");

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        assertThat(authentication).isNotNull();
        assertThat(authentication).isInstanceOf(UsernamePasswordAuthenticationToken.class);
        assertThat(authentication.getPrincipal()).isEqualTo("username");
    }

    @Test
    public void test_AuthorizeIfValid_shouldThrowBatTokenException() {
        when(verifier.verify(anyString())).thenThrow(TokenExpiredException.class);


        assertThatThrownBy(() -> service.authorizeIfTokenIsValid("invalidToken"))
                .isInstanceOf(BadTokenException.class);

    }
}
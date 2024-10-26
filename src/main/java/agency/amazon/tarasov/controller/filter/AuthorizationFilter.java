package agency.amazon.tarasov.controller.filter;

import agency.amazon.tarasov.service.security.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static agency.amazon.tarasov.constant.Constants.BEARER_TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {
    private final AuthService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI().startsWith("/auth")) authorize(request);
        filterChain.doFilter(request, response);
    }

    private void authorize(HttpServletRequest request) {
        var header = request.getHeader(AUTHORIZATION);
        if (containsBearerToken(header))
            service.authorizeIfTokenIsValid(header.substring(BEARER_TOKEN_PREFIX.length()));
    }

    private boolean containsBearerToken(String header) {
        return header != null && header.startsWith(BEARER_TOKEN_PREFIX);
    }
}

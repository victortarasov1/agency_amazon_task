package agency.amazon.tarasov.controller.filter;

import agency.amazon.tarasov.dto.ApiError;
import agency.amazon.tarasov.exception.BadTokenException;
import agency.amazon.tarasov.exception.BasicException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.NonNull;
import java.io.IOException;
import java.util.Optional;

import static org.apache.tomcat.websocket.Constants.UNAUTHORIZED;
import static org.springframework.http.HttpHeaders.WWW_AUTHENTICATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
@Order(0)
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    private final ObjectMapper mapper;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException | BadTokenException ex) {
            response.setHeader(WWW_AUTHENTICATE, ex.getMessage());
            response.setStatus(UNAUTHORIZED);
            var debugMessages = Optional.ofNullable(ex.getCause()).map(Throwable::getMessage).stream().toList();
            var error = new ApiError(ex.getMessage(), debugMessages);
            response.setContentType(APPLICATION_JSON_VALUE);
            mapper.writeValue(response.getOutputStream(), error);
        }
    }
}

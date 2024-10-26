package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.service.security.AuthService;
import agency.amazon.tarasov.service.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static agency.amazon.tarasov.constant.Constants.BEARER_TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    private final TokenProvider provider;

    @GetMapping("/login")
    public Map<String, String> authenticate(@RequestParam String email, @RequestParam String password) {
        return service.authenticate(email, password);
    }

    @GetMapping("/refresh")
    public  Map<String, String> refreshAccessToken(@RequestHeader(AUTHORIZATION) String authorizationHeader) {
        return provider.regenerateTokensIfRefreshTokenIsValid(authorizationHeader.substring(BEARER_TOKEN_PREFIX.length()));
    }
}

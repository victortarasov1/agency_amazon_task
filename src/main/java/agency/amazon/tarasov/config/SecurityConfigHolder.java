package agency.amazon.tarasov.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityConfigHolder {
    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access_token.time}")
    private long accessTokenTime;

    @Value("${jwt.refresh_token.time}")
    private long refreshTokenTime;
}
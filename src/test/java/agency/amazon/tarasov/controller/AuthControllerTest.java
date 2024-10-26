package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.service.security.AuthService;
import agency.amazon.tarasov.service.security.TokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthService service;

    @MockBean
    private TokenProvider provider;

    @Test
    public void testLogin() throws Exception {
        var email = "test@example.com";
        var password = "password";
        var expectedResponse = Map.of("token", "mocked-token");

        when(service.authenticate(email, password)).thenReturn(expectedResponse);

        mockMvc.perform(get("/auth/login")
                        .param("email", email)
                        .param("password", password)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mocked-token"));
    }

    @Test
    public void testRefresh() throws Exception {
        var tokenResponse = Map.of("accessToken", "newAccessToken", "refreshToken", "newRefreshToken");

        when(provider.regenerateTokensIfRefreshTokenIsValid(anyString())).thenReturn(tokenResponse);

        mockMvc.perform(get("/auth/refresh")
                        .header(AUTHORIZATION, "Bearer oldRefreshToken"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("newAccessToken"))
                .andExpect(jsonPath("$.refreshToken").value("newRefreshToken"));
    }

}
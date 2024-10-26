package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.service.account.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@example.com", roles = "USER")
class RestExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    void testHandleBasicException() throws Exception {
        mockMvc.perform(get("/auth/refresh")
                .header(AUTHORIZATION, "Bearer oldRefreshToken"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("authorization failed"));
    }

    @Test
    void testHandleUnexpectedException() throws Exception {
        mockMvc.perform(get("/auth/auth"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("No static resource auth/auth."));
    }
}
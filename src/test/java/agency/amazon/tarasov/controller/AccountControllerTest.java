package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.dto.AccountWithPasswordDto;
import agency.amazon.tarasov.model.Account;
import agency.amazon.tarasov.service.account.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test@example.com", roles = "USER")
class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;


    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void testAdd() throws Exception {
        var dto = new AccountWithPasswordDto("some email", "some password", "some name", "some surname");

        when(service.get(anyString())).thenReturn(dto.mapAccount());

        mockMvc.perform(post("/account/add")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGet() throws Exception {
        var account = new Account("test@example.com", "password", "some name", "some surname");

        when(service.get("test@example.com")).thenReturn(account);

        mockMvc.perform(get("/account/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(account.getEmail()))
                .andExpect(jsonPath("$.name").value(account.getName()))
                .andExpect(jsonPath("$.surname").value(account.getSurname()));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/account/delete"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdate() throws Exception {
        var dto = new AccountWithPasswordDto("test@example.com", "some password", "some name", "some surname");
        var account = dto.mapAccount();

        when(service.get(anyString())).thenReturn(account);

        mockMvc.perform(put("/account/update")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }
}
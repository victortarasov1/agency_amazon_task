package agency.amazon.tarasov.service.security;

import agency.amazon.tarasov.model.Account;
import agency.amazon.tarasov.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountDetailServiceTest {
    private UserDetailsService service;
    private AccountRepository repository;

    @BeforeEach
    public void setUp() {
        repository = mock(AccountRepository.class);
        service = new AccountDetailService(repository);
    }

    @Test
    void testLoadUserByUsername() {
        var details = new Account();
        details.setEmail("some email");

        when(repository.findById(anyString())).thenReturn(Optional.of(details));

        var result = service.loadUserByUsername(details.getUsername());

        assertNotNull(result);
        assertEquals(details, result);
    }

    @Test
    void testLoadUserByUsername_shouldThrowUserNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername(anyString()))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}

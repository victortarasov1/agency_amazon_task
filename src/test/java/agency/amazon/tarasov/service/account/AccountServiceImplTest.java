package agency.amazon.tarasov.service.account;

import agency.amazon.tarasov.exception.AccountAlreadyExistException;
import agency.amazon.tarasov.exception.AccountNotFoundException;
import agency.amazon.tarasov.model.Account;
import agency.amazon.tarasov.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountService accountService;
    private AccountRepository repository;
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        encoder = mock(PasswordEncoder.class);
        repository = mock(AccountRepository.class);
        accountService = new AccountServiceImpl(repository, encoder);
    }

    @Test
    public void testAdd() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());
        when(encoder.encode(anyString())).thenReturn(anyString());

        var account = new Account();

        accountService.add(account);

        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(any(Account.class));

    }

    @Test
    public void testAdd_shouldThrowAccountAlreadyExistException() {
        var account = new Account();
        account.setEmail("some email");

        when(repository.findById(anyString())).thenReturn(Optional.of(account));

        assertThatThrownBy(() -> accountService.add(account)).isInstanceOf(AccountAlreadyExistException.class);

        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).save(any(Account.class));

    }

    @Test
    public void testRemove() {
        accountService.remove("some email");

        verify(repository, times(1)).removeAccountByEmail(anyString());
    }

    @Test
    public void testGet() {
        var account = new Account();
        account.setEmail("some email");

        when(repository.findById(anyString())).thenReturn(Optional.of(account));

        var result = accountService.get("some email");

        assertThat(result).isEqualTo(account);
        verify(repository, times(1)).findById(anyString());

    }

    @Test
    public void testGet_shouldThrowAccountNotFoundException() {


        when(repository.findById(anyString())).thenReturn(Optional.empty());

       assertThatThrownBy(() -> accountService.get("email")).isInstanceOf(AccountNotFoundException.class);

    }

    @Test
    public void testUpdate_shouldThrowAccountNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> accountService.update("old email", new Account())).isInstanceOf(AccountNotFoundException.class);

    }

    @Test
    public void testUpdate_shouldThrowAccountAlreadyExistException() {
        var oldEmail = "old email";
        var newEmail = "new email";

        var account = new Account();
        account.setEmail(newEmail);


        when(repository.findById(anyString())).thenReturn(Optional.of(account));


        assertThatThrownBy(() -> accountService.update(oldEmail, account)).isInstanceOf(AccountAlreadyExistException.class);

    }

    @Test
    public void testUpdate() {
        var oldEmail = "old email";
        var newEmail = "new email";
        var password = "some password";
        var account = new Account();
        account.setEmail(newEmail);
        account.setPassword(password);

        when(encoder.encode(eq(password))).thenReturn(password);
        when(repository.findById(anyString())).thenReturn(Optional.of(account), Optional.empty());

        accountService.update(oldEmail, account);

        verify(repository, times(2)).findById(anyString());
        verify(encoder, times(1)).encode(anyString());
        verify(repository, times(1)).removeAccountByEmail(anyString());
        verify(repository, times(1)).save(any());

    }

}
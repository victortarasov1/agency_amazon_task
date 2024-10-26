package agency.amazon.tarasov.service.account;

import agency.amazon.tarasov.dto.AccountDto;
import agency.amazon.tarasov.exception.AccountAlreadyExistException;
import agency.amazon.tarasov.exception.AccountNotFoundException;
import agency.amazon.tarasov.model.Account;
import agency.amazon.tarasov.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public void add(Account account) {
        if (accountExists(account)) throw new AccountAlreadyExistException(account.getEmail());
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
    }

    @Override

    public void remove(String email) {
        repository.removeAccountByEmail(email);
    }

    @Override
    @CacheEvict(value = "accountCache", key = "#account.email")
    public void update(String oldEmail, Account account) {
        repository.findById(oldEmail).orElseThrow(() -> new AccountNotFoundException(oldEmail));
        if (!oldEmail.equals(account.getEmail()) && accountExists(account))
            throw new AccountAlreadyExistException(account.getEmail());
        repository.removeAccountByEmail(oldEmail);
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
    }

    @Override
    @Cacheable(value = "accountCache", key = "#email")
    public AccountDto get(String email) {
        return repository.findById(email).map(AccountDto::new).orElseThrow(() -> new AccountNotFoundException(email));
    }


    private boolean accountExists(Account account) {
        return repository.findById(account.getEmail()).isPresent();
    }
}

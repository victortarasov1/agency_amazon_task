package agency.amazon.tarasov.service.account;

import agency.amazon.tarasov.model.Account;

public interface AccountService {
    void add(Account account);
    void remove(String email);
    void update(String oldEmail, Account account);

    Account get(String email);
}

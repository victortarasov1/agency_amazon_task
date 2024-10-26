package agency.amazon.tarasov.dto;

import agency.amazon.tarasov.model.Account;

public record AccountDto(
        String email,
        String name,
        String surname
) {
    public AccountDto(Account account) {
        this(account.getEmail(), account.getName(), account.getSurname());
    }
}

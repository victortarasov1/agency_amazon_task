package agency.amazon.tarasov.dto;

import agency.amazon.tarasov.model.Account;

public record AccountWithPasswordDto(
        String email,
        String password,
        String name,
        String surname
) {
    public Account mapAccount() {
        return new Account(email, password, name, surname);
    }
}

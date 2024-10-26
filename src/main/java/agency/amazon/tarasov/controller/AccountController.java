package agency.amazon.tarasov.controller;

import agency.amazon.tarasov.dto.AccountDto;
import agency.amazon.tarasov.dto.AccountWithPasswordDto;
import agency.amazon.tarasov.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;


    @ResponseStatus(CREATED)
    @PostMapping("/add")
    public void add(@RequestBody AccountWithPasswordDto dto) {
        service.add(dto.mapAccount());
    }

    @GetMapping("/get")
    @PreAuthorize("hasRole('ROLE_USER')")
    public AccountDto get(Principal principal) {
        return new AccountDto(service.get(principal.getName()));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void delete(Principal principal) {
        service.remove(principal.getName());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_USER')")
    public void update(Principal principal, @RequestBody AccountWithPasswordDto dto) {
        service.update(principal.getName(), dto.mapAccount());
    }
}

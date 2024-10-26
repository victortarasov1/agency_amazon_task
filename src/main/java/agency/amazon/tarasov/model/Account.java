package agency.amazon.tarasov.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.List;

import static agency.amazon.tarasov.model.Role.ROLE_USER;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    @Id
    private String email;
    private String name;
    private String surname;
    private String password;

    private List<Role> roles = List.of(ROLE_USER);
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(Role::toString).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}

package agency.amazon.tarasov.repository;

import agency.amazon.tarasov.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<Account, String> {
    void removeAccountByEmail(String email);
}

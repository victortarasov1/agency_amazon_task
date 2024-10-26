package agency.amazon.tarasov.repository;

import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesAndTrafficByAsinRepository extends MongoRepository<SalesAndTrafficByAsin, String> {
}

package agency.amazon.tarasov.repository;

import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;

public interface SalesAndTrafficByDateRepository extends MongoRepository<SalesAndTrafficByDate, LocalDate> {
}

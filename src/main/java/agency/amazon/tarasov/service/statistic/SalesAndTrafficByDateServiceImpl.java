package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.exception.SalesAndTrafficNotFoundException;
import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import agency.amazon.tarasov.repository.SalesAndTrafficByDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesAndTrafficByDateServiceImpl implements SalesAndTrafficByDateService {
    private final SalesAndTrafficByDateRepository repository;
    @Override
    @Cacheable(value = "salesAndTrafficByDateCache", key = "#date")
    public SalesAndTrafficByDate findSalesAndTrafficByDate(LocalDate date) {
        return repository.findById(date).orElseThrow(() -> new SalesAndTrafficNotFoundException(date.toString()));
    }

    @Override
    @Cacheable(value = "salesAndTrafficByDateRangeCache", key = "#startDate + '_' + #endDate")
    public List<SalesAndTrafficByDate> findSalesAndTrafficByDateRange(LocalDate startDate, LocalDate endDate) {
        var dates = startDate.datesUntil(endDate.plusDays(1)).toList();
        return repository.findAllById(dates);
    }

    @Override
    @Cacheable(value = "allSalesAndTrafficByDateCache")
    public List<SalesAndTrafficByDate> findAllSalesAndTrafficByDate() {
        return repository.findAll();
    }
}

package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.exception.SalesAndTrafficNotFoundException;
import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.repository.SalesAndTrafficByAsinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesAndTrafficByAsinServiceImpl implements SalesAndTrafficByAsinService {
    private final SalesAndTrafficByAsinRepository repository;

    @Override
    @Cacheable(value = "salesAndTrafficByAsinCache", key = "#asin")
    public SalesAndTrafficByAsin findSalesAndTrafficByAsin(String asin) {
        return repository.findById(asin).orElseThrow(() -> new SalesAndTrafficNotFoundException(asin));
    }

    @Override
    @Cacheable(value = "salesAndTrafficByAsinRangeCache", key = "#range.toString()")
    public List<SalesAndTrafficByAsin> findSalesAndTrafficByAsinRange(List<String> range) {
        return repository.findAllById(range);
    }

    @Override
    @Cacheable(value = "allSalesAndTrafficByAsinCache")
    public List<SalesAndTrafficByAsin> findAllSalesAndTrafficByAsin() {
        return repository.findAll();
    }
}

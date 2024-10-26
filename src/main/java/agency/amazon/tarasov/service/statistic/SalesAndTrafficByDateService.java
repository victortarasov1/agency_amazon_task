package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.model.SalesAndTrafficByDate;

import java.time.LocalDate;
import java.util.List;

public interface SalesAndTrafficByDateService {
    SalesAndTrafficByDate findSalesAndTrafficByDate(LocalDate date);
    List<SalesAndTrafficByDate> findSalesAndTrafficByDateRange(LocalDate startDate, LocalDate endDate);
    List<SalesAndTrafficByDate> findAllSalesAndTrafficByDate();
}

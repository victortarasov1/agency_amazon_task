package agency.amazon.tarasov.service.statistic;

import agency.amazon.tarasov.model.SalesAndTrafficByAsin;

import java.util.List;

public interface SalesAndTrafficByAsinService {
    SalesAndTrafficByAsin findSalesAndTrafficByAsin(String asin);
    List<SalesAndTrafficByAsin> findSalesAndTrafficByAsinRange(List<String> range);
    List<SalesAndTrafficByAsin> findAllSalesAndTrafficByAsin();
}

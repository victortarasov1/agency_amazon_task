package agency.amazon.tarasov.dto;

import agency.amazon.tarasov.model.SalesAndTrafficByAsin;
import agency.amazon.tarasov.model.SalesAndTrafficByDate;
import lombok.Data;

import java.util.List;

@Data
public class Report {
    private ReportSpecification reportSpecification;
    private List<SalesAndTrafficByDate> salesAndTrafficByDate;
    private List<SalesAndTrafficByAsin> salesAndTrafficByAsin;
}

package agency.amazon.tarasov.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReportSpecification {
    private String reportType;
    private ReportOptions reportOptions;
    private Date dataStartTime;
    private Date dataEndTime;
    private List<String> marketplaceIds;
}

package agency.amazon.tarasov.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@Document
public class SalesAndTrafficByDate implements Serializable {
    @Id
    private LocalDate date;
    private Sales salesByDate;
    private Traffic trafficByDate;
}

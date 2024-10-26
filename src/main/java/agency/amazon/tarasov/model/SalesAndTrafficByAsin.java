package agency.amazon.tarasov.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document
public class SalesAndTrafficByAsin implements Serializable {
    @Id
    private String parentAsin;
    private Sales salesByAsin;
    private Traffic trafficByAsin;
}

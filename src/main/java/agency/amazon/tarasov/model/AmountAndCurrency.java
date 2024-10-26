package agency.amazon.tarasov.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AmountAndCurrency implements Serializable {
    private BigDecimal amount;
    private String currencyCode;
}
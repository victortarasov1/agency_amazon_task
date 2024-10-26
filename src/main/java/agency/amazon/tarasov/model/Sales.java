package agency.amazon.tarasov.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
public class Sales implements Serializable {
    private AmountAndCurrency orderedProductSales;
    private AmountAndCurrency orderedProductSalesB2B;
    private Integer unitsOrdered;
    private Integer unitsOrderedB2B;
    private Integer totalOrderItems;
    private Integer totalOrderItemsB2B;
    private AmountAndCurrency averageSalesPerOrderItem;
    private AmountAndCurrency averageSalesPerOrderItemB2B;
    private BigDecimal averageUnitsPerOrderItem;
    private BigDecimal averageUnitsPerOrderItemB2B;
    private AmountAndCurrency averageSellingPrice;
    private AmountAndCurrency averageSellingPriceB2B;
    private Integer unitsRefunded;
    private BigDecimal refundRate;
    private Integer claimsGranted;
    private AmountAndCurrency claimsAmount;
    private AmountAndCurrency shippedProductSales;
    private Integer unitsShipped;
    private Integer ordersShipped;
}

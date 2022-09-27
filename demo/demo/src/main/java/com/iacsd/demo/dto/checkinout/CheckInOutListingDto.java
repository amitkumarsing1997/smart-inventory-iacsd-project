package com.iacsd.demo.dto.checkinout;

import com.iacsd.demo.model.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CheckInOutListingDto {
    private String productName;
    private String productCode;
    private Long qty;
    private Unit unit;
    private Double rate;
    private Unit rateUnit;
    private Instant entryOn;

    public CheckInOutListingDto(String productName, String productCode, Long qty, Unit unit, Double rate,
                                Unit rateUnit, Instant entryOn) {
        this.productName = productName;
        this.productCode = productCode;
        this.qty = qty;
        this.unit = unit;
        this.rate = rate;
        this.rateUnit = rateUnit;
        this.entryOn = entryOn;
    }
}

package com.iacsd.demo.dto.bill;

import com.iacsd.demo.model.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProductInfoDTO {
    private String name;
    private String code;
    private Long quantity;
    private Unit unit;
    private Double rate;
    private Unit rateUnit;
    private Double total;

    public ProductInfoDTO(String name, String code, Long quantity, Unit unit, Double rate, Unit rateUnit) {
        this.name = name;
        this.code = code;
        this.quantity = quantity;
        this.unit = unit;
        this.rate = rate;
        this.rateUnit = rateUnit;
        this.total = rate * quantity;
    }
}

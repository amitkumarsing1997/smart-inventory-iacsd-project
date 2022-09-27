package com.iacsd.demo.dto.product;

import com.iacsd.demo.model.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductListingDto {
    private String uid;
    private String name;
    private String code;
    private Unit unit;
    private Long avlQty;

    public ProductListingDto(String uid, String name, String code, Unit unit, Long avlQty) {
        this.uid = uid;
        this.name = name;
        this.code = code;
        this.unit = unit;
        this.avlQty = avlQty;
    }
}

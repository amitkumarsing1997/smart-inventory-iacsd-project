package com.iacsd.demo.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCriteriaDto {
    private String categoryUid;
    private String productName;
    private Long accountId;
}

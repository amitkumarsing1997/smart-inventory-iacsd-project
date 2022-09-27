package com.iacsd.demo.dto;

import com.iacsd.demo.dto.product.ProductDto;
import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductCheckInDto {
    private ProductDto product;
    private List<CheckInDTO> checkIns;

    public static ProductCheckInDto from(Product product, List<CheckIn> checkIns) {
        ProductCheckInDto dto = new ProductCheckInDto();
        dto.setProduct(ProductDto.from(product));
        dto.setCheckIns(checkIns.stream().map(CheckInDTO::from).toList());
        return dto;
    }
}

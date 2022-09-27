package com.iacsd.demo.dto.checkinout;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckInOutCriteriaDto {
    private String productCode;
    private Long accountId;
}

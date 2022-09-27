package com.iacsd.demo.dto;

import com.iacsd.demo.model.CheckOut;
import lombok.Data;

@Data
public class CheckOutDto {
	private String checkInUid;
	private Long quantity;
//	private Unit unit;
	private Double rate;
//	private Unit rateUnit;

	public static void fillEntity(CheckOutDto dto, CheckOut entity) {
		 entity.setQuantity(dto.quantity);
//		 entity.setUnit(dto.unit);
		 entity.setRate(dto.rate);
//		 entity.setRateUnit(dto.rateUnit);
	 }
}

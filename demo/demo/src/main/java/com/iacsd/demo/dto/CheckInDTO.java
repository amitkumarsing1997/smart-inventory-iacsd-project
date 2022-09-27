package com.iacsd.demo.dto;

import com.iacsd.demo.model.CheckIn;
import com.iacsd.demo.model.Unit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class CheckInDTO {
    private String uid;
    private String productUid;
    private Long quantity;
    private Unit unit;
    private Double rate;
    private Unit rateUnit;
    private Instant checkInOn;
    private Instant expireOn;
    private Integer remindBefore;
    private Integer repeatReminder;
    private Long remainingQty;

    public static void fillEntity(CheckInDTO dto, CheckIn entity) {
        entity.setQuantity(dto.quantity);
        entity.setUnit(dto.unit);
        entity.setRate(dto.rate);
        entity.setRateUnit(dto.rateUnit);
        entity.setCheckedInOn(dto.checkInOn);
        entity.setExpireOn(dto.expireOn);
        entity.setRemindBefore(dto.remindBefore);
        entity.setRepeatReminder(dto.repeatReminder);
    }

    public static CheckInDTO from(CheckIn entity) {
        CheckInDTO dto = new CheckInDTO();
        dto.uid = entity.getUid();
        dto.quantity = entity.getQuantity();
        dto.unit = entity.getUnit();
        dto.rate = entity.getRate();
        dto.rateUnit = entity.getRateUnit();
        dto.checkInOn = entity.getCheckedInOn();
        dto.remindBefore = entity.getRemindBefore();
        dto.repeatReminder = entity.getRepeatReminder();
        dto.expireOn = entity.getExpireOn();
        dto.remainingQty = entity.getRemainingQuantity();
        return dto;
    }
}

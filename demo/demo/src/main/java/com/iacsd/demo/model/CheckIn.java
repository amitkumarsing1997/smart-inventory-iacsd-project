package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter @Setter
@Entity
@Table(name = "CHECK_IN")
public class CheckIn extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @Column(name = "QUANTITY")
    private Long quantity;
    @Column(name = "UNIT")
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @Column(name = "RATE")
    private Double rate;
    @Column(name = "RATE_UNIT")
    @Enumerated(EnumType.STRING)
    private Unit rateUnit;
    @Column(name = "CHECKED_IN_ON")
    private Instant checkedInOn;
    @Column(name = "EXP_ON")
    private Instant expireOn;
    @Column(name = "REMIND_BEFORE")
    private Integer remindBefore; //days
    @Column(name = "REPEAT_REMINDER")
    private Integer repeatReminder; //repeat in a day;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
    
    @Column(name="remaining_qty")
    private Long remainingQuantity;
    
//    @Column(name="sent_count")
    @Column(name="sent_count")
    private Integer sentCount;


}

package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter @Setter
@Entity
@Table(name = "CHECK_OUT")
public class CheckOut extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHECK_IN_ID")
    private CheckIn checkIn;
    @Column(name = "QTY")
    private Long quantity;
    @Column(name = "UNIT")
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @Column(name = "RATE")
    private Double rate;
    @Column(name = "RATE_UNIT")
    @Enumerated(EnumType.STRING)
    private Unit rateUnit;
    @Column(name = "CHECKED_OUT_ON")
    private Instant checkedOutOn;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BILL_ID")
    private Bill bill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;
}

package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "BILLING")
public class Bill extends BaseEntity {

    @Column(name = "CUST_NAME")
    private String custName;
    @Column(name = "MOBILE_NO")
    private String mobileNo;
    @Column(name = "TOTAL_AMT")
    private Double totalAmt;
    @Column(name = "PAID_AMT")
    private Double paidAmt;
    @Column(name = "PAYMENT_MODE")
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    @Column(name = "PAYMENT_STATUS")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bill", cascade = CascadeType.ALL)
    private List<CheckOut> checkOuts;

    @Column(name="CREATED_ON")
    private Instant createdOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @PrePersist
    public void setCreatedOn() {
        this.createdOn = Instant.now();
    }
}

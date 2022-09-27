package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter @Setter
@MappedSuperclass
public abstract class Auditable extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY")
    private AppUser createdBy;
    @Column(name = "CREATED_ON")
    private Instant createdOn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MODIFIED_BY")
    private AppUser modifiedBy;
    @Column(name = "MODIFIED_ON")
    private Instant modifiedOn;
}

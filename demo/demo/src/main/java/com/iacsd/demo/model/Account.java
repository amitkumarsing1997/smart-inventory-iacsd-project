package com.iacsd.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "ACCOUNT")
public class Account extends BaseEntity {
    @Column(name = "ACC_NAME")
    private String accName;
    @Column(name = "ADDRESS")
    private String address;
    
    @OneToOne(mappedBy = "account")
    private AppUser user;


}

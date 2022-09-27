package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name="PRODUCT_CATEGORY")
public class ProductCategory extends BaseEntity{

	@Column(name="NAME")
	private String name;
	@Column(name="IS_DELETED")	
	private Boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;

	@PrePersist
	void setIsDeletedDefault(){
		this.isDeleted = false;
	}

}

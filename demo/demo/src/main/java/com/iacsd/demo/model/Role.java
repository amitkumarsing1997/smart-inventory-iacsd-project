package com.iacsd.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Table(name = "ROLE")
public class Role extends BaseEntity {

	public static final String MERCHANT_ROLE="MERCHANT_USER";
	@Column(name = "NAME", length = 50, nullable = false)
	private String name;

	@Column(name = "CODE")
	private String code;

	@ManyToMany
	@JoinTable(name = "ROLE_PERMISSION", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID"))
	private List<Permission> permissions;

}

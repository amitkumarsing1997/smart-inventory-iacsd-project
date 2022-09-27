package com.iacsd.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "USER")
public class AppUser extends BaseEntity {

	@Column(name = "FULL_NAME")
	private String fullName;
	@Column(name = "USER_STATUS", length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private AppUserStatus status;
	@Column(name = "USER_NAME", length = 50, nullable = false)
	private String username;
	@Column(name = "PASSWORD", length = 500)
	private String password;
	@Column(name = "EMAIL")
	private String email;

	@ManyToMany
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;
	@Column(name = "USER_TYPE", length = 20)
	@Enumerated(EnumType.STRING)
	private UserType userType;

	//@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name = "ACC_ID")
	private Account account;

	public void addRole(Role role) {
		if (this.roles == null) {
			roles = new ArrayList<Role>();
		}
		roles.add(role);
	}

}

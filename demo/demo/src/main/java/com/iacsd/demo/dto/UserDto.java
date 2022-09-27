package com.iacsd.demo.dto;

import com.iacsd.demo.model.AppUser;
//import com.seeftech.smartstock.model.AppUserStatus;
//import com.seeftech.smartstock.model.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
	private String name;
	private String userName;
	private String password;
	//private UserType type;
	private String uid;
	private Long roleId;
	//private AppUserStatus status;

	public UserDto(String fullName, String userName, String uid) {
		this.name = fullName;
		this.userName = userName;
		//this.type = type;
		this.uid = uid;
		//this.status = userStatus;
	}	
	
	
	
	public UserDto(String fullName, String uid) {
		super();
		this.name = fullName;
		this.uid = uid;
	}

	public AppUser generateUser() {
		AppUser user = new AppUser();
		populateUser(user);
		return user;
	}

	public void populateUser(AppUser user) {
		user.setFullName(name);
		user.setUsername(userName);
		//user.setUserType(type);
	}
	
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public static UserDto of(AppUser user, UserDto userDto) {
		userDto.name = user.getFullName();
		//userDto.setStatus(user.getStatus());
		//userDto.setType(user.getUserType());
		userDto.setUserName(user.getUsername());
		return userDto;
	}
}

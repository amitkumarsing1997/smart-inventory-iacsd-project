package com.iacsd.demo.service;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.UserDto;
import com.iacsd.demo.dto.UserInfoDTO;
import com.iacsd.demo.dto.UserRegDTO;
import com.iacsd.demo.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public interface AppUserService {
	
	AppUser saveUpdateUser(UserDto userData);
	
	Boolean updateUserPassword(String userName,String password);
 	
	Boolean isUserNameExists(String userName);
	
	PaginationResponse<UserDto> getAllUsersByPage(Integer pageNo, Integer pageSize);

    Boolean regUser(UserRegDTO dto);

	AppUser getLoggedInUser();

	UserInfoDTO getUserInfo();
}

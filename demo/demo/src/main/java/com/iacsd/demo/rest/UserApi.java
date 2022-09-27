package com.iacsd.demo.rest;

import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.dto.UserInfoDTO;
import com.iacsd.demo.dto.UserRegDTO;
import com.iacsd.demo.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserApi {

	@Autowired
	private AppUserService userService;

	@GetMapping("/isUserNameExists/{userName}")
	public ApiResponse<Boolean>isUserNameExists(@PathVariable String userName) {

		//public ApiResponse<Boolean>isUserNameExists() {
		return new ApiResponse<Boolean>(userService.isUserNameExists(userName));
	}

	@PostMapping("reg")
	public ApiResponse<Boolean> regUser(@RequestBody UserRegDTO dto) {
		return ApiResponse.<Boolean> builder().body(this.userService.regUser(dto)).build();
	}

	@GetMapping()
	public ApiResponse<UserInfoDTO> getUserInfo() {
		return ApiResponse.<UserInfoDTO>builder().body(this.userService.getUserInfo()).build();
	}

}


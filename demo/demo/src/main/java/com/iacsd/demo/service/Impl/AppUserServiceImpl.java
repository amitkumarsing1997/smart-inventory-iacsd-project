package com.iacsd.demo.service.Impl;

import com.iacsd.demo.dto.PaginationResponse;
import com.iacsd.demo.dto.UserDto;
import com.iacsd.demo.dto.UserInfoDTO;
import com.iacsd.demo.dto.UserRegDTO;
import com.iacsd.demo.exception.ExceptionPool;
import com.iacsd.demo.model.*;
import com.iacsd.demo.repository.AppUserRepository;
import com.iacsd.demo.repository.RoleRepository;
import com.iacsd.demo.security.JwtUtil;
import com.iacsd.demo.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Slf4j
@Component
public class AppUserServiceImpl implements AppUserService {


	private static final Logger logger = LoggerFactory.getLogger(AppUserServiceImpl.class);
	@Autowired
	private AppUserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired private JwtUtil jwtUtil;

	@Override
	public AppUser saveUpdateUser(UserDto userData) {
		AppUser user = prepareUser(userData);
		Optional<Role> role = null;
		if (userData.getRoleId() != null) {
			role = roleRepository.findById(userData.getRoleId());
		} else {
			role = roleRepository.findById(1L);
		}
		role.ifPresent(user::addRole);
		userRepository.save(user);
		return user;
	}

	private AppUser prepareUser(UserDto userData) {
		AppUser user = null;
		if (StringUtils.hasText(userData.getUid())) {
			user = userData.generateUser();
			if (userData.getPassword() != null) {
				user.setPassword(this.passwordEncoder.encode(userData.getPassword()));
			}
			user.setStatus(AppUserStatus.ACTIVE);
		} else {
			user = userRepository.findByUid(userData.getUid());
			userData.populateUser(user);
		}
		return user;
	}

	@Override
	public UserInfoDTO getUserInfo() {
		AppUser appUser = this.getLoggedInUser();
		Account account = this.getLoggedInUser().getAccount();
		return UserInfoDTO.from(appUser, account);
	}


	@Override
	public Boolean isUserNameExists(String userName) {
		logger.info("Username hello :: {}",userName);
		return userRepository.isUserNameExists(userName);
	}

	@Override
	public Boolean updateUserPassword(String userName, String password) {
		AppUser user = userRepository.findByUsername(userName).orElseThrow(ExceptionPool.USR_INVALID);
		user.setPassword(this.passwordEncoder.encode(password));
		userRepository.save(user);
		return true;
	}

	@Override
	public PaginationResponse<UserDto> getAllUsersByPage(Integer pageNo, Integer pageSize) {
		Page<AppUser> page = userRepository.findAll(PageRequest.of(pageNo, pageSize));
		List<UserDto> userList = page.getContent().stream().map(user -> UserDto.of(user, new UserDto()))
				.toList();
		return new PaginationResponse<>(page.getNumber(), page.getSize(),
				page.getTotalPages(), page.getNumberOfElements(), page.getTotalElements(), userList);
	}

	@Override
	public Boolean regUser(UserRegDTO dto) {
		if (this.userRepository.existsByEmail(dto.getEmail())) {
			throw ExceptionPool.USR_EMAIL_EXISTS.get();
		}
		AppUser user = new AppUser();
		user.setFullName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getEmail());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setStatus(AppUserStatus.ACTIVE); //fixme mail based activation
		user.setUserType(UserType.INTERNAL_USER);
		Account account = new Account();
		account.setAccName(dto.getShopName());
		account.setAddress(dto.getAddress());
		user.setAccount(account);
		this.userRepository.save(user);
		return true;
	}

	@Override
	public AppUser getLoggedInUser() {
		return userRepository.findByUid(jwtUtil.getUid());
	}
}

package com.iacsd.demo.security;

import com.iacsd.demo.constants.MsgCode;
import com.iacsd.demo.exception.GenericException;
import com.iacsd.demo.model.AppUser;
import com.iacsd.demo.model.AppUserStatus;
import com.iacsd.demo.model.Role;
import com.iacsd.demo.repository.AppUserRepository;
import com.iacsd.demo.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("appUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsServiceImpl.class);

	@Autowired
	private AppUserRepository appUserRepo;

	@Autowired AppUserService appUserService;

	@Autowired
	private JwtUtil jwtUtil;

//	@Override
//	@Transactional(readOnly = true)
//	public UserDetails loadUserByUsername(String username) {
//		logger.info(" Username :: {}", username);
//		AppUser appUser = appUserRepo.findByUsername(username)
//				.orElseThrow(() -> new UsernameNotFoundException(username));
//		return new User(appUser.getUsername(), appUser.getPassword(), true, true, true,
//				true, List.of());
//	}


	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		logger.info(" Username :: {}", username);
		AppUser appUser = appUserRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new User(appUser.getUsername(), appUser.getPassword(), true, true, true,
				appUser.getStatus().equals(AppUserStatus.ACTIVE), findAllAuthorities(appUser.getRoles()));
	}

	private List<GrantedAuthority> findAllAuthorities(List<Role> appUserRoles) {
		return appUserRoles.stream().map((Role role) -> role.getCode()).map((String code) -> new SimpleGrantedAuthority(code))
				.collect(Collectors.toList());
	}


	public AuthResponse getAuthResponse(String username) {
		final AppUser appUser = appUserRepo.findByUsername(username).orElseThrow(() -> new GenericException("Invalid user", MsgCode.INVALID_USER));
		final AuthResponse authResponse = new AuthResponse();
		authResponse.setAccessToken(jwtUtil.generateToken(appUser));
		authResponse.setRefreshToken(jwtUtil.generateRefreshToken(appUser));
		authResponse.addAdditionalInfo("uid", appUser.getUid());
		authResponse.addAdditionalInfo("username", username);
		//authResponse.addAdditionalInfo("roles", appUser.getRoles().stream().map(Role::getCode).toList());
		log.debug("Response :: {}", authResponse);
		return authResponse;
	}
}


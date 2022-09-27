package com.iacsd.demo.security;

import com.iacsd.demo.constants.MsgCode;
import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.exception.GenericException;
import com.iacsd.demo.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "auth")
public class AuthApi {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private AppUserRepository appUserRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SecurityExTranslator exTranslator;

    @PostMapping("login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            String msg;
            String msgCode;
            msg = "Invalid credentials";
            msgCode = MsgCode.INVALID_CREDENTIAL;
            return new ResponseEntity<>(ApiResponse.<String>builder().success(false).body(msg).messageCode(msgCode).build(), HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ApiResponse.<AuthResponse>builder().body(((AppUserDetailsServiceImpl)userDetailsService).getAuthResponse(authRequest.getUsername())).messageCode(MsgCode.LOGIN_SUCCESS).build());
    }

    @PostMapping("refresh/token")
    public ResponseEntity<ApiResponse<?>> refreshToken(@RequestBody AuthRequest authRequest) {
        try {
            if (this.jwtUtil.isRefreshToken(authRequest.getRefreshToken())) {
                String username = this.jwtUtil.getUsername(authRequest.getRefreshToken());
                return ResponseEntity.ok(ApiResponse.<AuthResponse>builder().body(((AppUserDetailsServiceImpl)userDetailsService).getAuthResponse(username)).messageCode(MsgCode.LOGIN_SUCCESS).build());
            } else {
                throw  new GenericException("Not a refresh token", MsgCode.TOKEN_INVALID);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            ApiResponse<String> resp = this.exTranslator.translate(ex);
            return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
        }
    }

}

package com.iacsd.demo.security;

import com.iacsd.demo.constants.MsgCode;
import com.iacsd.demo.dto.ApiResponse;
import com.iacsd.demo.exception.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

@Component
public class SecurityExTranslator {

    public ApiResponse<String> translate(Exception ex) {

        String msg;
        String msgCode;

        if (ex instanceof ExpiredJwtException) {
            msg = "Token expired";
            msgCode = MsgCode.TOKEN_EXPIRED;
        }  else if (ex instanceof MalformedJwtException || ex instanceof SignatureException || ex instanceof UnsupportedJwtException) {
            msg = "Invalid JWT token";
            msgCode = MsgCode.TOKEN_INVALID;
        } else if (ex instanceof GenericException) {
            msg = ex.getMessage();
            msgCode = ((GenericException) ex).getMsgCode();
        } else {
            msg = "Error occurred";
            msgCode = MsgCode.SERVER_ERROR;
        }
        return ApiResponse.<String>builder().success(false).body(msg).messageCode(msgCode).build();
    }
}

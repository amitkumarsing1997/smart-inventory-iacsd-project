package com.iacsd.demo.constants;

public class MsgCode {
    private MsgCode() {}

    public static final String INVALID_CREDENTIAL = "credential_inv";
    public static final String LOGIN_SUCCESS = "login_success";
    public static final String TOKEN_INVALID = "token_inv";
    public static final String TOKEN_EXPIRED = "token_expired";

    public static final String REQ_INVALID = "req_inv";
    public static final String SERVER_ERROR = "server_err";


    //user
    public static final String USR_EMAIL_EXISTS = "usr_email_exists";
    public static final String INVALID_USER = "usr_inv";
}

package com.iacsd.demo.exception;

import com.iacsd.demo.constants.MsgCode;

import java.util.function.Supplier;

public class ExceptionPool {
    private ExceptionPool() {}


    //Generic
    public static final Supplier<GenericException> REQ_INVALID =  () -> new GenericException("Invalid Request", MsgCode.REQ_INVALID);

    //user
    public static final Supplier<GenericException> USR_EMAIL_EXISTS =  () -> new GenericException("Email already exists", MsgCode.USR_EMAIL_EXISTS);
    public static final Supplier<GenericException> USR_INVALID =  () -> new GenericException("Invalid user", MsgCode.INVALID_USER);

    //login


}

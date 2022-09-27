package com.iacsd.demo.exception;

public class GenericException extends RuntimeException{

	private static final long serialVersionUID = -4431042891936223335L;
	
	private final String msgCode;
	private Object data = false;

	public String getMsgCode() {
		return msgCode;
	}
	public Object getData() { return data; }
	
	public GenericException(String msgCode) {
		super(msgCode);
		this.msgCode=msgCode;
	}

	public GenericException(String msg, String msgCode) {
		super(msg);
		this.msgCode = msgCode;
	}

	public GenericException(Object data, String msg, String msgCode) {
		super(msg);
		this.msgCode = msgCode;
		this.data = data;
	}
}

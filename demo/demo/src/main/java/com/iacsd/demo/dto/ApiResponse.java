package com.iacsd.demo.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {

	@Builder.Default
	private boolean success = true;
	private String message;
	private T body;
	private String messageCode;

	public ApiResponse(T t) {
		this.body = t;
		this.success=true;
	}
	
	public ApiResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
}

package com.iacsd.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {

	@Builder.Default
	private boolean success = true;
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private int numberOfElements;
	private long totalElements;
	private List<T> body;
	
	public PaginationResponse(int pageNumber, int pageSize, int totalPages, int numberOfElements, long totalElements, List<T> body) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.numberOfElements = numberOfElements;
		this.totalElements = totalElements;
		this.setBody(body);
	}
	
	public PaginationResponse(int pageNumber, int pageSize, int totalPages, int numberOfElements, long totalElements) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.numberOfElements = numberOfElements;
		this.totalElements = totalElements;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setBody(List<T> body) {
		this.body = body;
	}
}

package org.ipni.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Response {
	private Long totalResults;
	private Long page;
	private Long totalPages;
	private Long perPage;
	private String sort;
	private List<Object> results;
}
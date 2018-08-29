package org.ipni.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class Response {
	private Long totalResults;
	private Long page;
	private Long totalPages;
	private Long perPage;
	private String sort;
	private String cursor;
	private List<Object> results;
}
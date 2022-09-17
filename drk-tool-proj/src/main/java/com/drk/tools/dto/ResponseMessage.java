package com.drk.tools.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {
	
	private boolean success;
	private String message;
	private String error;
	private String path;

}

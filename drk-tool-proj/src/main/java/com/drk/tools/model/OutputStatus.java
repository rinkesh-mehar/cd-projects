package com.drk.tools.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Component
@JsonInclude(content = Include.NON_EMPTY, value = Include.NON_NULL)
public class OutputStatus {
	private String ID;
	private String message;
	private String errorCode;
	private String error;
	private String msg;
	private String errCode;
	private boolean status;
	private long timestamp;
	private String publicUrl;
}

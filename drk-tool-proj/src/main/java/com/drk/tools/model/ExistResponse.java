package com.drk.tools.model;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ExistResponse {

	private boolean exist;

	Map<String, Object> metadata;

}

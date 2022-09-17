package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SymptomsModel {
	private int _symptomId;
	private String _symptom;
	private String _imageId;
	private String _genericImageId;
}

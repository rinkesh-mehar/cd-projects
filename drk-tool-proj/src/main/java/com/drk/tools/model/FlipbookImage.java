package com.drk.tools.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FlipbookImage {
	private int Id;
	private int symptomId;
	private String imageId;
	private int diagnosed;

	@Override
	public String toString() {
		return Id + "," + symptomId + ",'" + imageId + "'," + diagnosed + "";
	}

}

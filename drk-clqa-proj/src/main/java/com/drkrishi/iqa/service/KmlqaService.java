package com.drkrishi.iqa.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.drkrishi.iqa.model.KmlqaTaskListModel;
import com.drkrishi.iqa.model.ResponseMessage;

public interface KmlqaService {
	
	public ResponseMessage getKmlqaList(int userId);
	public ResponseMessage uploadKmlFile(MultipartFile kmlFile, String taskId);	
	public ResponseEntity downloadKmlFile(String filename);
	public ResponseMessage fileDetails(String taskId);
	public ResponseMessage submitDetails(String taskId, Double area, int userId);
}

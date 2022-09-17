package com.krishi.fls.services;

import org.springframework.web.multipart.MultipartFile;

import com.krishi.fls.model.Response;

public interface FileManagementService {
	
	public Response saveFile(MultipartFile file);

}

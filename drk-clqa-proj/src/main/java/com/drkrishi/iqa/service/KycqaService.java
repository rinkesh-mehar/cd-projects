package com.drkrishi.iqa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.drkrishi.iqa.model.KycDetailsModel;
import com.drkrishi.iqa.model.KycqaTaskListModel;
import com.drkrishi.iqa.model.ResponseMessage;

public interface KycqaService {
	
	public ResponseMessage getKycqaList(int userId);
	public ResponseMessage getKycDetails(String farmerId, String taskId,int userId);
	public ResponseMessage saveKycDetails(KycDetailsModel kycDetailsModel);
	public ResponseMessage kycCorrection(KycDetailsModel kycDetailsModel);
	public ResponseMessage uploadFile(MultipartFile file);
}

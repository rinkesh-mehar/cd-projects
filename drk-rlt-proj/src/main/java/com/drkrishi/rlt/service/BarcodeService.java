package com.drkrishi.rlt.service;

import java.util.List;

import com.drkrishi.rlt.model.BarcodeModel;
import com.drkrishi.rlt.model.BarcodeRequestModel;
import com.drkrishi.rlt.model.ResponseMessage;

public interface BarcodeService {

	public List<BarcodeModel> getSampleList(int rltId);
	public ResponseMessage getBarcodeDetails(String barcodeNumber,int loggedinUserId);
	public ResponseMessage saveBarcodeDetails(BarcodeRequestModel barcodeRequestModel);
	public ResponseMessage setNeedMoreSample(String taskId, int userId);
}

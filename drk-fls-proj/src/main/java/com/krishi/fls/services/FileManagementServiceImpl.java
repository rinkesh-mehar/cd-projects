package com.krishi.fls.services;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.krishi.fls.entity.StaticData;
import com.krishi.fls.model.Error;
import com.krishi.fls.model.Response;
import com.krishi.fls.repository.StaticDataRepository;

@Service
public class FileManagementServiceImpl implements FileManagementService {
	
	@Autowired
	private StaticDataRepository staticDataRepository;

	@Transactional
	@Override
	public Response saveFile(MultipartFile file) {
		Response response = new Response();
		List<StaticData> data = staticDataRepository.findByDataKeyIn(List.of("docBasePath", "zipPath"));
		String basePath = data.stream().filter(d -> d.getDataKey().equals("docBasePath")).findFirst().get().getDataValue();
		basePath = basePath +  data.stream().filter(d -> d.getDataKey().equals("zipPath")).findFirst().get().getDataValue();
		File newFile = new File(basePath, file.getOriginalFilename());
		try(BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
			bos.write(file.getBytes());
			response.setSuccess("success");
		} catch (IOException e) {
			e.printStackTrace();
			Error err = new Error();
			err.setErrorMesg(e.getMessage());
			response.setError(err);
		}
		return response;
	}

}

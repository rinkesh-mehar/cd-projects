package com.drkrishi.rlt.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drkrishi.rlt.dao.ManageHardwareDao;
import com.drkrishi.rlt.entity.Users;
import com.drkrishi.rlt.model.ManageHardwareModel;
import com.drkrishi.rlt.model.ResponseMessage;

@Service
public class ManageHarwareServiceImpl implements ManageHardwareService {
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private ManageHardwareDao manageHardwareDao;

	@Override
	public ResponseMessage getAssignedHardwardList(Integer userId, String searchKeyward) {
		ResponseMessage response = new ResponseMessage();
		Users user = manageHardwareDao.getUserById(userId);
		List<Object[]> objects = manageHardwareDao.getAssignedHardwardList(user.getRegionId(), searchKeyward);
		List<ManageHardwareModel> l = new ArrayList<>();
		objects.forEach(o -> {
			ManageHardwareModel m = new ManageHardwareModel();
			m.setId((Integer) o[0]);
			m.setBoxid((String) o[1]);
			m.setVanid((String) o[2]);
			m.setDate(FORMAT.format((Date) o[3]));;
			m.setName((String) o[4]);
			m.setMobilenumber((String) o[5]);
			m.setRole((String) o[6]);
			l.add(m);
		});
		response.setStatusCode(200);
		response.setMessage("Success");
		response.setData(l);
		return response;
	}

	@Override
	public ResponseMessage unTagHardware(ManageHardwareModel manageHardwareModel) {
		ResponseMessage response = new ResponseMessage();
		try {
			int count = manageHardwareDao.unTagHardware(manageHardwareModel.getId(), manageHardwareModel.getUserId());
			if(count == 1) {
				response.setStatusCode(200);
				response.setMessage("Success");
			} else {
				response.setStatusCode(204);
				response.setMessage("Not found");
			}
			
		} catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Internal Server Error");
		}
		return response;
	}

}

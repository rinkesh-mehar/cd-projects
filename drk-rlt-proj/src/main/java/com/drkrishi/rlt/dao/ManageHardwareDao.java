package com.drkrishi.rlt.dao;

import java.util.List;

import com.drkrishi.rlt.entity.Users;

public interface ManageHardwareDao {

	List<Object[]> getAssignedHardwardList(Integer regionId, String searchKeyward);
	
	Users getUserById(Integer userId);

	int unTagHardware(Integer id, Integer userId);

}

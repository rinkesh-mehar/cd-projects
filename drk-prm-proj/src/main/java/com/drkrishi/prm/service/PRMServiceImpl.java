package com.drkrishi.prm.service;

import com.drkrishi.prm.dao.PRMDao;
import com.drkrishi.prm.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PRMServiceImpl implements PRMService{

	@Autowired
	private PRMDao prmDao;
	
	// get user details based on reporting to id
	@Override
	public List<DrKrishiRole> findUserByReportingToId(int id) {
		return prmDao.findUserByReportingToId(id);
	}

	//get list of all states
	@Override
	public List<State> getStates() {
		return prmDao.getStates();
	}

	
	// get all villages based on region id
	@Override
	public List<Village> findVillagesByRegion(int regionCode) {
		return prmDao.findVillagesByRegion(regionCode);
	}

	//get villages based on villages
	@Override
	public List<Village> findVillagesBytalika(int talukaCode) {
		return prmDao.findVillagesBytalika(talukaCode);
	}

	//Get PR Scouts based on PRM and region
	@Override
	public List<DRKrishiUser> findScoutByReportingId(int userId, int regionId) {
		return prmDao.findScoutByReportingId(userId, regionId);
	}
	
	//Get PR Scouts based on PRM
	@Override
	public List<DRKrishiUser> findScoutByPRmId(int userId) {
		return prmDao.findScoutByPRmId(userId);
	}

	//get village info based on village
	@Override
	public List<Village_Info> findVillagesInfoByVillage(int villageCode) {
		return prmDao.findVillagesInfoByVillage(villageCode);
	}

	//Get villages by district
	@Override
	public List<Village> findVillagesbyDistrict(int districtCode) {
		return prmDao.findVillagesbyDistrict(districtCode);
	}

	//Get region based on state
	@Override
	public List<Region> findRegionByState(int stateCode) {
		return prmDao.findRegionByState(stateCode);
	}

	//Get villages based on tile id
	@Override
	public List<Village> findVillagesByTileId(int tileId) {
		return prmDao.findVillagesByTileId(tileId);
	}

	

}

package com.drkrishi.prm.service;

import com.drkrishi.prm.model.*;

import java.util.List;

public interface PRMService {

	public List<DrKrishiRole> findUserByReportingToId(int id);

	public List<State> getStates();

	public List<Village> findVillagesByRegion(int regionCode);

	public List<Village> findVillagesBytalika(int talukaCode);

	public List<DRKrishiUser> findScoutByReportingId(int userId, int regionId);

	public List<Village_Info> findVillagesInfoByVillage(int villageCode);

	public List<Village> findVillagesbyDistrict(int districtCode);

	public List<Region> findRegionByState(int stateCode);

	public List<Village> findVillagesByTileId(int tileId);

	public List<DRKrishiUser> findScoutByPRmId(int userId);

}

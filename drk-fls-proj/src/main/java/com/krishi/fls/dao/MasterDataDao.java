package com.krishi.fls.dao;

import com.krishi.fls.model.MasterData;

public interface MasterDataDao {
	
	public MasterData getMasterData(Integer userId, Integer stateId, Integer regionId);

}

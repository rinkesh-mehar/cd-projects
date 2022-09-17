package com.drkrishi.prm.service;

import com.drkrishi.prm.model.ResponseMessage;
import com.drkrishi.prm.model.SearchPrmAssigntask;
import com.drkrishi.prm.model.Villagetask;

import java.util.List;

public interface SearchPrsService {

	public List<Villagetask> getAssignmentListByPrmIdSearch(SearchPrmAssigntask search);

	public ResponseMessage getScoutNamesListByStateidAndRegionid(int userId,int stateId, int regionId);

	
}

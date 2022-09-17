package com.krishi.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GstmDataDaoImpl implements GstmDataDao {
	
	@PersistenceContext
	private EntityManager em;
	
	

	@Transactional
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findAllGstmBenchmark() {
		final String hqlQuery = "select ts.id, ts.gstmSpotId, t.entityId, v.regionId, v.subregionId"
				+ " from TaskSpot as ts inner join Task as t on ts.taskId = t.id inner join FarmCase as fc on t.entityId = fc.id " 
				+ " inner join FarmerFarm as ff on fc.farmId = ff.id inner join Farmer as f on ff.farmerId = f.farmerId inner "
				+ " join Village as v on f.villageId = v.id " 
				+ " where ts.isBenchmark = 1 and (ts.isGstmSynced = null or ts.isGstmSynced = 0)";
		Query query = em.createQuery(hqlQuery);
		
		List<Object[]> results = query.getResultList();
		return results;
	}



	@Transactional
	@Override
	public void updateSyncStatus(List<String> taskSpotIds) {
		final String hqlQuery = "update TaskSpot set isGstmSynced = 1 where id in (:ids)";
		Query query = em.createQuery(hqlQuery).setParameter("ids", taskSpotIds);
		query.executeUpdate();
	}

}

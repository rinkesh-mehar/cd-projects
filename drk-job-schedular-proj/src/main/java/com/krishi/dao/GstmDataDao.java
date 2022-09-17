package com.krishi.dao;

import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;

public interface GstmDataDao {

	public List<Object[]> findAllGstmBenchmark();

	public void updateSyncStatus(List<String> taskSpotIds);

}

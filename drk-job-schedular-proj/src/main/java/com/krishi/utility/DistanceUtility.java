package com.krishi.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DistanceUtility {

	/*
	 * public static void main(String[] args) throws java.lang.Exception {
	 * List<Map<Integer, Map<String, Double>>> list = new ArrayList<Map<Integer,
	 * Map<String, Double>>>();
	 * 
	 * for (int i = 0; i < 5; i++) { Map<Integer, Map<String, Double>> testMap = new
	 * HashMap<Integer, Map<String, Double>>(); Map<String, Double> langLatMap = new
	 * HashMap<String, Double>(); langLatMap.put("lat", 12.120000 + i * 200);
	 * langLatMap.put("lon", 76.680000 + i * 200); testMap.put(i, langLatMap);
	 * list.add(testMap); }
	 * 
	 * List<Map<Integer, Map<String, Double>>> newlist = new ArrayList<Map<Integer,
	 * Map<String, Double>>>(); int listSize = list.size(); Double lat = 24.879999;
	 * Double lon = 24.879999; for (int i = 0; i < listSize; i++) {
	 * 
	 * list = listDistance(lat, lon, list, "M"); Map<Integer, Map<String, Double>>
	 * obj = new HashMap<Integer, Map<String, Double>>(); obj = sortList(list);
	 * list.remove(obj);
	 * 
	 * for (Entry<Integer, Map<String, Double>> dataVal : obj.entrySet()) { lat =
	 * dataVal.getValue().get("lat"); lon = dataVal.getValue().get("lon"); }
	 * newlist.add(obj); }
	 * 
	 * System.out.println("newlist :- " + newlist); }
	 */

	
	public List<Map<Integer, Map<String, Double>>> getSortedVillageByDistance(Double lat, Double lon, List<Map<Integer, Map<String, Double>>> list){
		List<Map<Integer, Map<String, Double>>> newlist = new ArrayList<Map<Integer, Map<String, Double>>>();
		int listSize = list.size();
		/*
		 * Double lat = 24.879999; Double lon = 24.879999;
		 */
		for (int i = 0; i < listSize; i++) {

			list = listDistance(lat, lon, list, "M");
			Map<Integer, Map<String, Double>> obj = new HashMap<Integer, Map<String, Double>>();
			obj = sortList(list);
			list.remove(obj);

			for (Entry<Integer, Map<String, Double>> dataVal : obj.entrySet()) {
				lat = dataVal.getValue().get("lat");
				lon = dataVal.getValue().get("lon");
			}
			newlist.add(obj);
		}
		
		return newlist;
	}
	
	
	
	
	private static Map<Integer, Map<String, Double>> sortList(List<Map<Integer, Map<String, Double>>> list) {

		Map<Integer, Map<String, Double>> obj = new HashMap<Integer, Map<String, Double>>();
		for (Map<Integer, Map<String, Double>> map : list) {
			for (Map<String, Double> data : map.values()) {
				if (obj.isEmpty()) {
					obj = map;
				} else {
					for (Map<String, Double> in : obj.values()) {
						if (in.get("dist") > data.get("dist")) {
							obj = map;
						}
					}
				}
			}
		}

		return obj;
	}

	private static List<Map<Integer, Map<String, Double>>> listDistance(double lat1, double lon1,
			List<Map<Integer, Map<String, Double>>> list, String unit) {
		double dist = 0;
		for (Map<Integer, Map<String, Double>> map : list) {

			for (Entry<Integer, Map<String, Double>> dataMap : map.entrySet()) {

				Double lat2 = dataMap.getValue().get("lat");
				Double lon2 = dataMap.getValue().get("lon");
				if ((lat1 == lat2) && (lon1 == lon2)) {

					dist = 0;
					dataMap.getValue().put("dist", dist);

				} else {
					double theta = lon1 - lon2;
					dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
							+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
									* Math.cos(Math.toRadians(theta));
					dist = Math.acos(dist);
					dist = Math.toDegrees(dist);
					dist = dist * 60 * 1.1515;
					if (unit.equals("K")) {
						dist = dist * 1.609344;
					} else if (unit.equals("N")) {
						dist = dist * 0.8684;
					}
					dataMap.getValue().put("dist", dist);
				}
			}
		}

		return list;

	}

	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2))
					+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit.equals("K")) {
				dist = dist * 1.609344;
			} else if (unit.equals("N")) {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}

}

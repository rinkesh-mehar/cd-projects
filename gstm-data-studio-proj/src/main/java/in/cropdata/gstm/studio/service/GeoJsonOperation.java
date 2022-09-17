package in.cropdata.gstm.studio.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.gstm.studio.exceptions.InvalidDataException;
import in.cropdata.gstm.studio.model.MapData;

import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to convert list data to GeoJson
 *
 * @author Rinkesh Mehar on 04/07/20
 * @since 1.0
 */

@Service
public class GeoJsonOperation
{
    private static final Logger LOGGER = LoggerFactory.getLogger(GeoJsonOperation.class);

    private static final String APPLICATION_NAME = "gstm-data";

    @SuppressWarnings({ "unchecked" })
	public String convertMapDataToGeoJson(final List<MapData> mapDataList)
    {
        JSONObject featureCollection = new JSONObject();
        JSONArray features = new JSONArray();
        final ObjectMapper mapper = new ObjectMapper();

        try
        {
        	Integer id = 0;
            for (final MapData mapData : mapDataList)
            {
            	/** Preparing coordinates */
                JSONArray coord1 = new JSONArray();
                coord1.add(0, mapData.getMinX());
                coord1.add(1, mapData.getMinY());

                JSONArray coord2 = new JSONArray();
                coord2.add(0, mapData.getMinX());
                coord2.add(1, mapData.getMaxY());

                JSONArray coord3 = new JSONArray();
                coord3.add(0, mapData.getMaxX());
                coord3.add(1, mapData.getMaxY());

                JSONArray coord4 = new JSONArray();
                coord4.add(0, mapData.getMaxX());
                coord4.add(1, mapData.getMinY());

                JSONArray coord5 = new JSONArray();
                coord5.add(0, mapData.getMinX());
                coord5.add(1, mapData.getMinY());

                JSONArray coordinatesArray = new JSONArray();
                coordinatesArray.add(coord1);
                coordinatesArray.add(coord2);
                coordinatesArray.add(coord3);
                coordinatesArray.add(coord4);
                coordinatesArray.add(coord5);
                
                JSONArray coordinates = new JSONArray();
                coordinates.add(coordinatesArray);

                /** Creation of geometry */
                JSONObject geometry = new JSONObject();
                geometry.put("type", "Polygon");
                geometry.put("coordinates", coordinates);

                /** Creation of Feature Properties */
                JSONObject featureProperties = new JSONObject();
                featureProperties.put("Color", mapData.getColor());
                featureProperties.put("TileID", mapData.getTileId());

                /** Creation of Feature */
                JSONObject featureObject = new JSONObject();
                featureObject.put("type", "Feature");
				featureObject.put("id", id);
                featureObject.put("properties", featureProperties);
                featureObject.put("geometry", geometry);
                id++;
                /** Adding Features to Feature Array */
                features.add(featureObject);

                /** Creation of crs property */
                JSONObject crsProperty = new JSONObject();
                crsProperty.put("name", "urn:ogc:def:crs:OGC:1.3:CRS84");

                /** Creation of crs */
                JSONObject crs = new JSONObject();
                crs.put("type","name");
                crs.put("properties", crsProperty);

                featureCollection.put("type", "FeatureCollection");
                featureCollection.put("name", APPLICATION_NAME);
                featureCollection.put("crs", crs);
                featureCollection.put("features", features);
            }

            String infoMsg = featureCollection.isEmpty() ? "Geo Json data is null..." : "Geo Json done...";
			LOGGER.info(infoMsg);

            return mapper.writeValueAsString(featureCollection);

        } catch (Exception e)
        {
            throw new InvalidDataException("Exception in Processing GeoJson ", e.getMessage());
        }
    }
}

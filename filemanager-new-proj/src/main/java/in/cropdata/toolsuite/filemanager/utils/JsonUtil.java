package in.cropdata.toolsuite.filemanager.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 
     * @param t - JSON object as a String
     * @return FileMetadata
     */
    public static Map<String, Object> jsonToMap(String t) {

	Map<String, Object> map = new HashMap<String, Object>();

	if (t != null && !t.equals("")) {

	    JSONObject jObject = new JSONObject(t);
	    Iterator<?> keys = jObject.keys();
	    try {
		while (keys.hasNext()) {
		    String key = (String) keys.next();
		    Object value = jObject.get(key);
		    map.put(key, value);
		}
	    } catch (Exception e) {
		log.error("JSON PARSE ERROR: ", e);
	    }

	}
	return map;
    }// metadataJsonToFileMetadata

    /**
     * String objectToJSON(Object obj)
     * 
     * @param obj
     * @return String
     */
    public static String objectToJSON(Object obj) {
	String json = null;
	if (obj != null) {
	    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
	    json = gson.toJson(obj);
	}

	return json;
    }

}// ModelMapperUtil

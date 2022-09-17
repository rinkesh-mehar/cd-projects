/**
 * 
 */
package in.cropdata.farmers.app.utils;


import org.springframework.stereotype.Component;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Component
public class GenerateFarmerAppID {


	public String generateKeysForApp(String type) {

		return type.concat(String.valueOf(System.currentTimeMillis()));

	}

}

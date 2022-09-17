/**
 * 
 */
package in.cropdata.farmers.app.utils;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenerateKeyUtils {

	 public static String generateKey(String entity) {
//		 	String finalKey = null;
			StringBuffer key = new StringBuffer(entity);
//			key.append(String.valueOf(generateRandom()));
//			System.err.println("key : " + key);
			key.append(System.nanoTime());			
//			System.err.println("final key 1 : " + key + " length : " + key.length());
//			finalKey = String.valueOf(key).length() < 22 ? String.valueOf(key) : String.valueOf(key).substring(0, String.valueOf(key).length() - (String.valueOf(key).length() - 21));
//			System.err.println("final key 2 : " + finalKey + " length : " + finalKey.length());
			return String.valueOf(key).length() < 22 ? String.valueOf(key) : String.valueOf(key).substring(0, String.valueOf(key).length() - (String.valueOf(key).length() - 21));
		}
	 
   	 //random number between 0- 9
	 public static int generateRandom() {

		// create instance of Random class   
		    Random rand = new Random();   
		    // Generate random integers in range 0 to 9   
		    int randNumber = rand.nextInt(10);     
//		    System.err.println("Random Integers: "+randNumber); 
		    
		    return randNumber;
		}
}

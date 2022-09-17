/**
 * 
 */
package in.cropdata.toolsuite.filemanager.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author siddhant
 *
 */
public class ApiUtil {

    /**
     * @author siddhant
     */
    public ApiUtil() {
	// TODO Auto-generated constructor stub
    }

    /**
     * Encodes the URL.
     *
     * @param url the url
     * @return the string
     * 
     * @author siddhant
     */
    public static String encode(String url)  
    {  
              try {  
                   String encodeURL=URLEncoder.encode( url, "UTF-8" );  
                   return encodeURL;  
              } catch (UnsupportedEncodingException e) {  
                   return "Issue while encoding" +e.getMessage();  
              }  
    }//encode
    
    /**
     * Decodes the URL in UTF-8 format.
     *
     * @author siddhant
     * @param url the url
     * @return the string
     */
    public static String decode(String url)  
    {  
              try {  
                   String prevURL="";  
                   String decodeURL=url;  
                   while(!prevURL.equals(decodeURL))  
                   {  
                        prevURL=decodeURL;  
                        decodeURL=URLDecoder.decode( decodeURL, "UTF-8" );  
                   }  
                   return decodeURL;  
              } catch (UnsupportedEncodingException e) {  
                   return "Issue while decoding" +e.getMessage();  
              }  
    }//decode
}

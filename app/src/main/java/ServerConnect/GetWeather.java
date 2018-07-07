package ServerConnect;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Build;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
public class GetWeather {

	
	String url = null;
	String httpUrl = "http://apis.baidu.com/apistore/weatherservice/weather?citypinyin=";
	String httpArg = "beijing";
	static String apikey = "6811c9c27bb1bb33a52ee457a4f10da6";
	
	
	//String jsonResult = request(httpUrl, httpArg);
	//System.out.println(jsonResult);
	
	

	/**
	 * @param urlAll
	 *          
	 * @param httpArg
	 *           
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
	        // apikey HTTP header
	        connection.setRequestProperty("apikey", apikey);
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return result;
	}
	

}


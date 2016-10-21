package ServerConnect;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
public class GetInfo {

	private String baseuserURL="http://1.tsbxapptest.applinzi.com/JsonServlet?user=";
	private String basepassURL="http://1.tsbxapptest.applinzi.com/JsonServlet?pass=";
	private String basecityURL="http://1.tsbxapptest.applinzi.com/JsonServlet?user_city=";
	private String baseregisterURL = "http://1.tsbxapptest.applinzi.com/JsonServlet?";
	private String basefriendURL = "http://1.tsbxapptest.applinzi.com/JsonServlet?";
	private String basemessageURL = "http://1.tsbxapptest.applinzi.com/JsonServlet?";
	
	String url = null;
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public boolean get_username(String user){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = baseuserURL + user;
		System.out.println(url);
		String username = "default";
		System.out.println("debug :1 username : "+user);
		
		@SuppressWarnings("deprecation")
		HttpGet httpGet = new HttpGet(url);
		@SuppressWarnings("deprecation")
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			username = showResponseResult(response);
			System.out.println("debug 2get the :"+username);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		

		if(username.equals(user))
			return true;
		else
			return false;
	}
	public boolean get_friend(String user1,String user2){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basefriendURL + "friend1="+user1+"&friend2="+user2;
		System.out.println(url);
		String username = "default";
		System.out.println("debug :11 friend : "+user1+"  "+user2);
		
		@SuppressWarnings("deprecation")
		HttpGet httpGet = new HttpGet(url);
		@SuppressWarnings("deprecation")
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			username = showResponseResult(response);
			System.out.println("debug 12get the user2 is:"+username);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		

		if(username.equals(user1) || username.equals(user2))
			return true;
		else
			return false;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public String make_friends(String user1,String user2){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basefriendURL + "makefriend1="+user1+"&makefriend2="+user2;
		System.out.println("URL is :"+url);
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(result == null)
			return "false";
		else if(result.equals("null"))
			return "false";
		return result;
	}
	
	public String delete_friends(String user1,String user2){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basefriendURL + "deletefriend1="+user1+"&deletefriend2="+user2;
		System.out.println("URL is :"+url);
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(result == null)
			return "false";
		else if(result.equals("null"))
			return "false";
		return result;
	}
	
	public String[] get_friends_all(String user){
		String [] friends_list = new String [1000];
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basefriendURL + "friendall="+user;
		
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			 friends_list = showResponseResults(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return friends_list;
	}
	
	public String get_message(String user1,String user2){
		//msg_user1,msg_user2,message
		//msg_delusr1,msg_deluser2
		//msg_updateuser1
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basemessageURL + "msg_user1="+user1+"&msg_user2="+user2;
		System.out.println("debug; "+url);
		String message = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			message = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return message;
	}
	

	public String insert_message(String user1,String user2,String message){
		//msg_user1,msg_user2,message
		//msg_delusr1,msg_deluser2
		//msg_updateuser1
		System.out.println("this is insert message");

		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basemessageURL + "msg_user1="+user1+"&msg_user2="+user2+"&message="+message;
		System.out.println(url);
		String result = "false";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public String update_message(String user1,String user2,String message){
		//msg_user1,msg_user2,message
		//msg_delusr1,msg_deluser2
		//msg_updateuser1
		System.out.println("this is update message");
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basemessageURL + "msg_updateuser1="+user1+"&msg_updateuser2="+user2+"&msg_update="+message;
		System.out.println(url);
		String result = "false";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	public String delete_message(String user1,String user2){
		//msg_user1,msg_user2,message
		//msg_delusr1,msg_deluser2
		//msg_updateuser1
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basemessageURL + "msg_deluser1="+user1+"&msg_deluser2="+user2;
		System.out.println(url);
		String result = "false";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public String get_password(String user){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basepassURL + user;
		String password = "mengwei";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			password = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return password;
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public String get_usercity(String user){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = basecityURL + user;
		String usercity = "shanghai";
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			usercity = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		return usercity;
	}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressLint("NewApi")
	public String registernow(String user,String pass,String user_city){
		StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		url = null;
		url = baseregisterURL + "user_register="+user+"&pass_register="+pass+"&user_city="+user_city;
		System.out.println("URL is :"+url);
		String result = null;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		try{
			HttpResponse response = httpClient.execute(httpGet);
			result = showResponseResult(response);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if(result == null)
			return "false";
		else if(result.equals("null"))
			return "false";
		return result;
	}
	
	public String showResponseResult(HttpResponse response){
		String resultString = null;
		String line = null;
		if(null == response){
			return "hello";
		}
		
		HttpEntity httpEntity = response.getEntity();
		try {
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			
			while(null != (line = reader.readLine())){
				resultString = line;
			}
			System.out.println("debug:show"+resultString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (resultString == null)
			return "false";
		else if(resultString.equals("null"))
			return "false";
		return resultString;
	}
	public String[] showResponseResults(HttpResponse response){
		String [] resultString = new String [1000];
		String line = null;
		if(null == response){
			return null;
		}
		
		HttpEntity httpEntity = response.getEntity();
		try {
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			int i = 0;
			
			while(null != (line = reader.readLine())){
				resultString[i] = line;
				i++;
			}
			System.out.println("debug:show"+resultString);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if (resultString == null)
			return null;
		else if(resultString.equals("null"))
			return null;
		return resultString;
	}

	public String encryption(String plainText) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			re_md5 = buf.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return re_md5;
	}
}

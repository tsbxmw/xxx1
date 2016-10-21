package com.example.app_test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.bitmap.MainActivity;
import com.google.zxing.client.android.CaptureActivity;

import ServerConnect.GetInfo;
import ServerConnect.GetWeather;
import ServerConnect.QueryTask;
import android.app.Activity;
import android.app.DownloadManager.Query;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginSuccess extends Activity{
	
	private TextView textview = null;
	private GetWeather getWeather = new GetWeather();
	private Button ScanButton;
	private final static int SCANNIN_GREQUEST_CODE = 1;

	String httpUrl = "http://apis.baidu.com/apistore/weatherservice/weather?citypinyin=";
	String httpArg = "beijing";
	private String result;
	private int flag = 0;
	String city;
	static String apikey = "6811c9c27bb1bb33a52ee457a4f10da6";
	private QueryTask queryTask = new QueryTask();
	private String output = null;
	private GetInfo getInfo = new GetInfo();
	private Bundle bundle;
	private Handler myHandler ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_success);
		textview = (TextView)findViewById(R.id.text_view);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		String user  = bundle.getString("user");
		city = getInfo.get_usercity(user);
		System.out.println(user + city);
		//ScanButton = (Button)findViewById(R.id.button_scan);
		
		Message message = new Message();
		message.what = 0;
		
		myHandler = new Handler() {  
	          public void handleMessage(Message msg) {   
	               switch (msg.what) {   
	                    case 0:   
	                         ShowWeather(city);  
	                         break;   
	                    case 1:
	                    	 LoginSuccess();
	               }   
	               super.handleMessage(msg);   
	          }

			
	     };  
	     myHandler.sendMessageDelayed(message, 1000);
	     
	}
	public void ShowWeather(final String city){
		Thread start = new Thread(){
		public void run(){
				flag = 0;
				httpArg = city;
				System.out.println(httpUrl+httpArg);
				
				Thread thread = new Thread(){
					public void run(){
						result  = getWeather.request(httpUrl, httpArg);
						if (result != null) {
				            try {
				                JSONObject jsonObject = new JSONObject(result);
				                String resultCode = jsonObject.getString("errNum");
				                System.out.println(resultCode);
				                if (resultCode.equals("0")) {
				                    
				                    String retData = jsonObject.getString("retData");
				                    JSONObject resultJsonObject = new JSONObject(retData);
				                    output = "city" + ": " + resultJsonObject.getString("city") + "\n"
				                            + "date" + ": " + resultJsonObject.getString("date") + "\n"
				                            + "time" + ": " + resultJsonObject.getString("time") + "\n"
				                            + "weather" + ": " + resultJsonObject.getString("weather") + "\n"
				                            + "tmp" + ": " + resultJsonObject.getString("temp") + "\n"
				                            + "low" + ": " + resultJsonObject.getString("l_tmp") + "\n"
				                            + "hith" + ": " + resultJsonObject.getString("h_tmp") + "\n"
				                            + "WD" + ": " + resultJsonObject.getString("WD") + "\n"
				                            + "WS" + ": " + resultJsonObject.getString("WS") + "\n"
				                            + "sunrise" + ": " + resultJsonObject.getString("sunrise") + "\n"
				                            + "sunset" + ": " + resultJsonObject.getString("sunset") + "\n";
				                   
				                } else if (!resultCode.equals("0")) {
				                    String reason = jsonObject.getString("reason");
				                   
				                } else {
				                    
				                  
				                }
				            } catch (JSONException e) {
				                // TODO Auto-generated catch block
				                e.printStackTrace();
				            }
				        } else {
				          
				            
				        }
						
						
						flag = 1;
					}
				};
				thread.start();
				while(flag == 0){
					
				}
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessageDelayed(message, 1000);
			}
		};
		start.start();
		
		//textview.setText(result);
	}
	public void LoginSuccess(){
		textview.setText(output);
		
		System.out.println(result);
	}
	public void ScanBitmap(View view){
		Intent intent = new Intent();
		/*
		intent.setClass(LoginSuccess.this,com.google.zxing.client.android.CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
		*/
		intent.setClass(LoginSuccess.this, com.google.zxing.client.android.CaptureActivity.class);
		startActivity(intent);
	}
	
	public void Back(View view){
		super.onBackPressed();
	}
	
}

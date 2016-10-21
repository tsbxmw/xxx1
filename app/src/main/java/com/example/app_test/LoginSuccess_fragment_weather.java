package com.example.app_test;


	import org.json.JSONException;
import org.json.JSONObject;

import ServerConnect.GetInfo;
import ServerConnect.GetWeather;
import ServerConnect.QueryTask;
import android.app.Activity;
import android.app.Fragment;  
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
	  
	public class LoginSuccess_fragment_weather  extends Fragment  
	{  
		private TextView textview = null;
		private TextView user_text = null;
		private GetWeather getWeather = new GetWeather();
		 final static int SCANNIN_GREQUEST_CODE = 1;
		private TextView cityname = null;
		private Button Citychange = null;
		
		
		String httpUrl = "http://apis.baidu.com/apistore/weatherservice/weather?citypinyin=";
		String httpArg = "beijing";
		private String result;
		private int flag = 0,flag_1 = 0;
		String city;
		static String apikey = "6811c9c27bb1bb33a52ee457a4f10da6";
		private QueryTask queryTask = new QueryTask();
		private String output = "Get Weather Wrong , Try Again !";
		
		private GetInfo getInfo = new GetInfo();
		private Bundle bundle;
		private Handler myHandler ;
		private String user ;
		private String [] cityinfo = new String[20];
	  
	    @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
	    	View  view = inflater.inflate(R.layout.login_success, container,false);
	    	textview = (TextView )view.findViewById(R.id.text_view);
	    	user_text = (TextView)view.findViewById(R.id.user_name);
	    	cityname = (TextView)view.findViewById(R.id.city);
	    	Citychange = (Button)view.findViewById(R.id.ChangeCity);
			bundle = getArguments();
			user = bundle.getString("user");
			user_text.setText(user);
			
			System.out.println(user + city);
			
			 OnClickListener scan = null;
		        scan = new OnClickListener() {
		            public void onClick(View v) {
		                System.out.println("xxxxxxxxxx");
		                ChangeCity(getView());
		            }
		        };
		        Citychange.setOnClickListener(scan);
			Message message = new Message();
			message.what = 0;
			
			myHandler = new Handler() {  
		          public void handleMessage(Message msg) {   
		               switch (msg.what) {   
		                    case 0:   
		                         ShowWeather();  
		                         break;   
		                    case 1:
		                    	 LoginSuccess();
		               }   
		               super.handleMessage(msg);   
		          }

				
		     };  
		     myHandler.sendMessage(message);
		     //this.setArguments(bundle);
	        return view;  
	    }  
	    
	    public void ShowWeather(){
			Thread start = new Thread(){
			public void run(){
				try{
					city = bundle.getString("city");
					System.out.println("debug city is " +city);
				}catch (Exception e){
					
						e.printStackTrace();
					
				}
				if(city==null){
					Thread getcity = new Thread(){
						public void run(){
							flag_1 =0 ;
							city = getInfo.get_usercity(user);
							flag_1 = 1;
						}
					};
					getcity.start();
					while(flag_1 == 0){
					
					}
					flag = 0;
				}
					
					if( (city.equals("null") || city.equals("false")))
						city = "shanghai";
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
					                    cityinfo[0] = resultJsonObject.getString("city");
					                    cityinfo[1] = resultJsonObject.getString("date");
					                    cityinfo[2] = resultJsonObject.getString("time");
					                    cityinfo[3] = resultJsonObject.getString("weather");
					                    cityinfo[4] = resultJsonObject.getString("temp");
					                    cityinfo[5] = resultJsonObject.getString("l_tmp");
					                    cityinfo[6] = resultJsonObject.getString("h_tmp");
					                    cityinfo[7] = resultJsonObject.getString("WD");
					                    cityinfo[8] = resultJsonObject.getString("WS");
					                    cityinfo[9] = resultJsonObject.getString("sunrise");
					                    cityinfo[10] = resultJsonObject.getString("sunset");
					                    output = "城市" + " : " + resultJsonObject.getString("city") + "\n"
					                            +"日期" + " : " + resultJsonObject.getString("date") + "\n"
					                            + "时间" + " : " + resultJsonObject.getString("time") + "\n"
					                            + "天气" + " : " + resultJsonObject.getString("weather") + "\n"
					                            + "温度" + " : " + resultJsonObject.getString("temp") + "\n"
					                            + "低温" + " : " + resultJsonObject.getString("l_tmp") + "\n"
					                            + "高温" + " : " + resultJsonObject.getString("h_tmp") + "\n"
					                            + "风向" + " : " + resultJsonObject.getString("WD") + "\n"
					                            + "风速" + " : " + resultJsonObject.getString("WS") + "\n"
					                            + "日出" + " : " + resultJsonObject.getString("sunrise") + "\n"
					                            + "日落" + " : " + resultJsonObject.getString("sunset") + "\n";
					                   
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
					myHandler.sendMessage(message);
				}
			};
			start.start();
			
			//textview.setText(result);
		}
		public void LoginSuccess(){
			System.out.println("debuginfo : cituinfo[0]" + cityinfo[0]);
			cityname.setText(cityinfo[0]);
			textview.setText(output);
			
			System.out.println(result);
		}
		
		
		public void ChangeCity(View view){
		     
			 Intent intent = new Intent();
			 intent.setClass(getActivity(), CityList.class);
			 intent.putExtras(bundle);
			 if(getActivity()!=null){
				 getActivity().startActivity(intent);
				 
			 }
			
		}
		
		
		
		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			
		}
	}


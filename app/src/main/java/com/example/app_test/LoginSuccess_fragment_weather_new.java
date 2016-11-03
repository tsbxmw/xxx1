package com.example.app_test;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import ServerConnect.GetInfo;
import ServerConnect.GetWeather;
import ServerConnect.QueryTask;

public class LoginSuccess_fragment_weather_new extends Fragment
{


	final static int SCANNIN_GREQUEST_CODE = 1;
    String httpURL_new = "http://m.moji.com/aqi/china/";
	String endurl = null;
    private String result;
    private int flag = 0,flag_1 = 0;
    String city;
    private GetInfo getInfo = new GetInfo();
    private Bundle bundle;
    private Handler myHandler ;
    private String user ;
    private String [] cityinfo = new String[20];
	private WebView webView = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View  view = inflater.inflate(R.layout.fragment_weather_new, container,false);
        bundle = getArguments();
        user = bundle.getString("user");
		webView = (WebView)view.findViewById(R.id.weather);

        System.out.println(user + city);

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
                while(flag_1 == 0){}
                flag = 0;
            }
                //to avoid the FATAL EXCEPTION FATAL EXCEPTION: Thread-929
//				Process: com.example.app_test, PID: 12723
//				java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference
//				at com.example.app_test.LoginSuccess_fragment_weather$3.run(LoginSuccess_fragment_weather.java:119)
            //modify : mengwei 201611021555

                if( city!=null && (city.equals("null") || city.equals("false")))
                    city = "shanghai/shanghai";
                else if(city == null)
                    city = "shanghai/shanghai";
                else
                    city = city+"/"+city;
                System.out.println("MENGWEI_INFO:city end is " + city);
				endurl = httpURL_new+city;
                Message message = new Message();
                message.what = 1;
                myHandler.sendMessage(message);
            }
        };
        start.start();

        //textview.setText(result);
    }
    public void LoginSuccess(){
        Log.v("MENGWEIINFO:",endurl);
        webView.loadUrl(endurl);
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url){
				view.loadUrl(url);
				return true;
			}
		});

    }






    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
}


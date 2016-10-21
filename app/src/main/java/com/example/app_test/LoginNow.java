package com.example.app_test;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import GifView.GifView;
import ServerConnect.GetInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import ServerConnect.GetInfo;

public class LoginNow extends Activity{
	
	LoginSuccess_fragment_weather lsg = new LoginSuccess_fragment_weather();
	public String username	="ma",password ="ma";
	public String user_get ,pass_get;
	public GetInfo getinfo = new GetInfo();
	boolean result = false;
	private Bundle bundle;
	private Handler myHandler ;
	int flag = 0;
	private GifView gifview;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loginnow);
		gifview = (GifView)findViewById(R.id.gif);
		gifview.setMovieResource(R.drawable.gif_login_new);
		Message message = new Message();
		message.what = 0;
		
		myHandler = new Handler() {  
	          public void handleMessage(Message msg) {   
	               switch (msg.what) {   
	                    case 0:   
	                         LoginNow();  
	                         break;   
	                    case 1:
	                    	 LoginWait();
	               }   
	               super.handleMessage(msg);   
	          }

			
	     };  
	     myHandler.sendMessage(message);
	     
	}
	private void LoginWait() {
		// TODO Auto-generated method stub
	if(result ){
	
			
			pass_get = getinfo.encryption(pass_get);
			System.err.println("Debug 6: "+user_get + pass_get);
			if(pass_get.equals(password)){
					/*
					Intent toLoginSuccess = new Intent(LoginNow.this,LoginSuccess.class);
					toLoginSuccess.putExtras(bundle);
					startActivity(toLoginSuccess);
					*/
					Intent test_main = new Intent(LoginNow.this,test_main.class);
					test_main.putExtras(bundle);
					//lsg.setArguments(bundle);
					startActivity(test_main);
					
					LoginNow.this.finish(); 
			}
			else{
				new AlertDialog.Builder(LoginNow.this).setTitle("Login Failed")
				.setMessage("Wrong password , rewrite it")
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						System.out.print("debug 7:rewrite password");
						Intent tologin = new Intent(LoginNow.this,MainActivity.class);
						startActivity(tologin);
						LoginNow.this.finish();
					}
				})
				.show();
			}
		}else{
			new AlertDialog.Builder(LoginNow.this).setTitle("Login Failed")
			.setMessage("Wrong user name , rewrite it")
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					System.out.println("debug : rewrite username");
					Intent tologin = new Intent(LoginNow.this,MainActivity.class);
					startActivity(tologin);
					LoginNow.this.finish();
				}
			})
			.show();
		}
	}   
	public void LoginNow(){
		flag = 0;
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		user_get = bundle.getString("user");
		pass_get = bundle.getString("pass");
		System.out.println("Debug 3username == :: "+user_get + pass_get);
		Thread start = new Thread(){
			public void run(){
				
				Thread thread = new Thread(){
					public void run(){
						flag = 0;
						result = getinfo.get_username(user_get);
						System.out.println("Debug 4result = ::" + result);
						password = getinfo.get_password(user_get);
						System.out.println("Debug 5password of  = ::" + user_get + ":"+password);
						flag = 1;
						System.out.println("debug: flag"+flag);
					}
				};
				
				thread.start();
				while(flag == 0){
				     //System.out.println(flag);
					
				}
				Message message = new Message();
				message.what = 1;
				myHandler.sendMessage(message);
				
			}
		};
		start.start();
		
		
	}
}
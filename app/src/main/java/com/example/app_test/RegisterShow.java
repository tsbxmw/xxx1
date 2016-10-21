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

public class RegisterShow extends Activity{
	
	
	private String username;
	private String user_city = "shanghai";
	private boolean getuser;
	private String user_get,pass_get,pass_again_get,result;
	private int flag = 0;
	private GetInfo getinfo = new GetInfo();
	private Bundle bundle;
	private int flag_1 = 0;
	private Handler myHandler ;
	private GifView gifshow;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registershow);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		user_get = bundle.getString("user_get");
		pass_get = bundle.getString("pass_get");
		pass_again_get = bundle.getString("pass_again_get");
		gifshow = (GifView)findViewById(R.id.gif);
		gifshow.setMovieResource(R.drawable.gif_login);
		Message message = new Message();
		message.what = 3;
		
		myHandler = new Handler() {  
	          public void handleMessage(Message msg) {   
	               switch (msg.what) {   
	                    case 0:   
	                    	 RegisterOnMysql();
	                         break;   
	                    case 1:
	                    	 Registering();
	                    	 break;
	                    case 2:
	                    	 RegisterSuccess();
	                    	 break;
	                    case 3:
	                    	 getuser();
	                    	 break;
	                    	 
	               }   
	               super.handleMessage(msg);   
	          }

			
	     };  
	     myHandler.sendMessageDelayed(message, 1000);
		
	}
	public void RegisterOnMysql(){
	
		Thread thread_out = new Thread(){
			public void run(){
				Thread thread = new Thread(){
					public void run(){
						flag_1 = 0;
						result = getinfo.registernow(user_get, pass_get,user_city);
					    flag_1 = 1;
					}
				};
				thread.start();
				while(flag_1 == 0){
				     //System.out.println(flag);
					
				}
				System.out.println("debug result is " + result );
				Message message = new Message();
				message.what = 2;
				myHandler.sendMessage(message);
			};
		};
		thread_out.start();
	}
	public void RegisterSuccess(){
		if(result.equals("true")){
			System.err.println("debug this is result :"+result +user_get+pass_get);
			Intent toregistersuccess = new Intent(RegisterShow.this,RegisterSuccess.class);
			startActivity(toregistersuccess);
			RegisterShow.this.finish();
		}
		else{
			new AlertDialog.Builder(RegisterShow.this).setTitle("Regist Failed")
			.setMessage("Server error , check your network and retry it !")
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					
					Intent back = new Intent(RegisterShow.this,RegisterNow.class);
					startActivity(back);
					RegisterShow.this.finish();
				}
			})
			.show();
		}
	}
	public void  getuser(){
		Thread thread_out = new Thread(){
			public void run(){
				Thread thread = new Thread(){
					public void run(){
						flag=0;
						getuser = ! getinfo.get_username(user_get);
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
		thread_out.start();
		
			
	}
	public void Registering(){

		System.err.println("debug:"+user_get+pass_get+pass_again_get);
	
		if(getuser) 
		{
				if( !(user_get.equals("") && pass_get.equals("")))
				{
					if(pass_get.equals(pass_again_get))
					{
						Message message1 = new Message();
						message1.what = 0;
						myHandler.sendMessage(message1);
						
						
					}
					else
					{
						new AlertDialog.Builder(RegisterShow.this).setTitle("Regist Failed")
						.setMessage("Defirent password ! , rewrite it")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								System.out.print("debug 7:rewrite password");
								Intent back = new Intent(RegisterShow.this,RegisterNow.class);
								startActivity(back);
								RegisterShow.this.finish();
							}
						})
						.show();
					}
					
				}
				else{
						new AlertDialog.Builder(RegisterShow.this).setTitle("Regist Failed")
						.setMessage("Null user or password ! , rewrite it")
						.setPositiveButton("YES", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// TODO Auto-generated method stub
								System.out.print("debug 7:rewrite password");
								Intent back = new Intent(RegisterShow.this,RegisterNow.class);
								startActivity(back);
								RegisterShow.this.finish();
							}
						})
						.show();
					
				}
				
		}
		else{
			new AlertDialog.Builder(RegisterShow.this).setTitle("Regist Failed")
			.setMessage("username has already have! , rewrite it")
			.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					System.out.print("debug 7:rewrite password");
					Intent back = new Intent(RegisterShow.this,RegisterNow.class);
					startActivity(back);
					RegisterShow.this.finish();
				}
			})
			.show();
		
		}
	}
}
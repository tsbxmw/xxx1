package com.example.app_test;

//test for webview_test branch
import java.security.MessageDigest;

import android.app.Activity;
import android.app.AlertDialog;
import ServerConnect.GetInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddFriend extends Activity {

	private EditText friendname=null;
	private Button button_back = null;
	private Button button_add = null;
	private Bundle bundle;
	private GetInfo getinfo = new GetInfo();
	private Handler myHandler ;
	private String passString ;
	private String username;
	private TextView result;
	private String add_result;
	private int flag =0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_friend);
		
		//setContentView(R.layout.activity_login);
		button_back = (Button)findViewById(R.id.button_back);
		button_add = (Button)findViewById(R.id.button_add);
		friendname = (EditText)findViewById(R.id.friendname);
		result = (TextView)findViewById(R.id.result);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		
		username = bundle.getString("user");
		Message message = new Message();
		message.what = 1;
		
		myHandler = new Handler() {  
	          public void handleMessage(Message msg) {   
	               switch (msg.what) {   
	                    case 0:   
	                    	add(username,passString);
	                    	break;
	                    case 1:
	                    	System.out.println("do noting");
	                    	break;
	                    case 2:
	                    	showresult();
	               }   
	               super.handleMessage(msg);   
	          }

			

			

			
	     };  
	     myHandler.sendMessage(message);
		
	}
	
	private void showresult() {
		// TODO Auto-generated method stub
		System.out.print(add_result);
		if(add_result.equals("true"))
			result.setText("ADD SUCCESSFUL\nYOU ARE FRIENDS NOW!");
		else if(add_result.equals("false"))
			result.setText("ADD FAILED TRY AGAIM  ");
		else if(add_result.equals("wrong"))
			result.setText("ID WRONG \n NOT EXIST !");
	}
	public void ADD_FRIEND(final View view){
		result.setText("add now ... ");
		Message message = new Message();
		message.what = 0;
		myHandler.sendMessage(message);
	
	}

	public void add(String user1,String user2)
	{
	
		passString = friendname.getText().toString();
		Thread out = new Thread(){
			
			public void run (){
				flag = 0;
				Thread thread = new Thread(){
					public void run(){
						flag = 0;
						add_result = "false";
						System.out.println(username+"+++"+passString);
						if(passString!=null && !passString.equals("")){
							boolean exist = getinfo.get_username(passString);
							if(exist)
								add_result = getinfo.make_friends(username, passString);
							else
								add_result = "wrong";
						}
							
						else
							System.out.println("null friend name");
						flag = 1;
					}
				};
				thread.start();
				while(flag == 0)
				{
					
				}
				Message message = new Message();
				message.what = 2;
				myHandler.sendMessage(message);
			}
			
			
		};
		out.start();


	}
	public void Back(View view){
		super.onBackPressed();
		this.finish();
	}
	
}
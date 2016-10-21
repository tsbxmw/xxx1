package com.example.app_test;

import java.security.PublicKey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.MediaItem.Flags;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import ServerConnect.GetInfo;
public class RegisterNow extends Activity{
	private EditText user = null;
	private EditText pass = null;
	private EditText pass_again = null;
	private Button button_register = null;
	//private String username,password,passwordagain;
	private String username;
	private String user_city = "shanghai";
	private String user_get,pass_get,pass_again_get,result;
	

	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		user = (EditText )findViewById (R.id.user_new);
		pass = (EditText)findViewById(R.id.pass_new);
		pass_again = (EditText)findViewById(R.id.pass_again);
		button_register = (Button)findViewById(R.id.button_register);
		
		
	}
	public void RegisterNow(View view){
		
		
		user_get = user.getText().toString();
		pass_get = pass.getText().toString();
		pass_again_get = pass_again.getText().toString();
		Bundle bundle = new Bundle();
		bundle.putString("user_get", user_get);
		bundle.putString("pass_get", pass_get);
		bundle.putString("pass_again_get", pass_again_get);
		Intent toregistersuccess = new Intent(RegisterNow.this,RegisterShow.class);
		toregistersuccess.putExtras(bundle);
		startActivity(toregistersuccess);
		RegisterNow.this.finish();
		
	
	}
	public void Back(View view){
		super.onBackPressed();
		this.finish();
	}
	
	
}

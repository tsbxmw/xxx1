package com.example.app_test;


import java.security.MessageDigest;

import android.app.Activity;
import android.app.AlertDialog;
import ServerConnect.GetInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class MainActivity extends Activity {

	private EditText user=null;
	private EditText pass=null;
	private Button button_back = null;
	private Button button_login = null;
	private Button button_register = null;
	private CheckBox remember = null;
	private CheckBox autologin = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		//setContentView(R.layout.activity_login);
		button_back = (Button)findViewById(R.id.button_back);
		button_login = (Button)findViewById(R.id.button_login);
		button_register = (Button)findViewById(R.id.button_register);
		user =(EditText)findViewById(R.id.user);
		pass = (EditText)findViewById(R.id.pass);
		remember = (CheckBox)findViewById(R.id.remember);
		autologin = (CheckBox)findViewById(R.id.autologin);
		Context context = MainActivity.this;
		SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
		String user_from_file = sp.getString("user", "");
		String pass_from_file = sp.getString("pass", "");
		System.out.println(user_from_file);
		boolean passremberb = sp.getBoolean("remember",false);
		boolean autologinb = sp.getBoolean("autologin",false);
		if(user_from_file!=null && passremberb)
			user.setText(user_from_file);
		user.setText(user_from_file);
		if(pass_from_file!=null && passremberb) {
			pass.setText(pass_from_file);

			if(autologinb)
				autologin.setChecked(true);
		}

		if(passremberb)
			remember.setChecked(true);
		if(autologinb){
			LoginNow(button_login);
		}
		
	}
	
	public void LoginNow(final View view){
		String userString = user.getText().toString();
		String passString = pass.getText().toString();

		Context context = MainActivity.this;
	    SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
		Editor editor = sp.edit();
		if(remember.isChecked())
			editor.putBoolean("remember",true);
		else
			editor.putBoolean("remember",false);
		if(autologin.isChecked())
			editor.putBoolean("autologin",true);
		else
			editor.putBoolean("autologin",false);
		editor.putString("user", userString);
		editor.putString("pass",passString);

		editor.commit();
		Bundle bundle = new Bundle();
		Intent toRegister = new Intent(MainActivity.this,LoginNow.class);
		bundle.putString("user", userString);
		bundle.putString("pass", passString);
		toRegister.putExtras(bundle);
		startActivity(toRegister); 
		this.finish();
	}
	public void RegisterNow(View view){
		
		Intent toRegister = new Intent(MainActivity.this,RegisterNow.class);
		startActivity(toRegister); 
		
	}
	public void Back(View view){
		super.onBackPressed();
		this.finish();
	}
	
}
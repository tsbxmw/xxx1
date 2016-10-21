package com.example.app_test;

import com.google.zxing.client.android.CaptureActivity;

import ServerConnect.GetInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CityList extends Activity{



	private Bundle bundle;
	private String username;
	private Button YES;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.citychange);
		YES = (Button)findViewById(R.id.Back);
		
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		
		username = bundle.getString("user");
		
		
	}
	public void CityChose(View view)
	{
		System.out.println(view.getId());
		TextView temp = (TextView)findViewById(view.getId());
		String city = temp.getText().toString();
		bundle.putString("city", city);
		Intent intent = new Intent();
	    intent.setClass(CityList.this, test_main.class);
	    intent.putExtras(bundle); 
	    startActivity(intent);
	    finish();
		
		
	}

	
	
	public void BACK(View view){
		super.onBackPressed();
		this.finish();
	}
	
	//: Could not find method BACK(View) in a parent or ancestor Context for android:onClick attribute defined on view clas
	
}

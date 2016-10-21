package com.example.app_test;

import GifView.GifView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class RegisterSuccess extends Activity{
	

	private GifView gifshow = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_success);
		gifshow = (GifView)findViewById(R.id.gif);
		gifshow.setMovieResource(R.drawable.register_success);
		new Handler().postDelayed(new Runnable() {    
            public void run() {   
                //你需要跳转的地方的代码  
            	Intent tologin = new Intent(RegisterSuccess.this,MainActivity.class);
    			startActivity(tologin);
    			RegisterSuccess.this.finish();
                finish();  
            }    
        }, 2000);
		
			
		
	}
	public void Back(View view){
		super.onBackPressed();
	}
	
}

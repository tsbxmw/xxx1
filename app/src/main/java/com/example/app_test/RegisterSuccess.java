package com.example.app_test;

import GifView.GifView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.io.IOException;

public class RegisterSuccess extends Activity{
	

	private GifView gifshow = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.register_success);
//		gifshow = (GifView)findViewById(R.id.gif);
//		gifshow.setMovieResource(R.drawable.register_success);
		try {
			GifImageView gifImageView1 = (GifImageView) findViewById(R.id.gif);
			GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.startshow);

			gifImageView1.setImageDrawable(gifDrawable);
		}catch (IOException e){
			e.printStackTrace();
		}
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

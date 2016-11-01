package com.example.app_test;




import GifView.GifView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
public class StartShow extends Activity{
	

	private GifView gifshow ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.startshow);
		//gifshow = (GifView)findViewById(R.id.gif);
		//gifshow.setMovieResource(R.drawable.startshow);
        try {
            GifImageView gifImageView1 = (GifImageView) findViewById(R.id.gif1);
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.startshow);

            gifImageView1.setImageDrawable(gifDrawable);
        }catch (IOException e){
            e.printStackTrace();
        }
        new Handler().postDelayed(new Runnable() {
            public void run() {   
                //����Ҫ��ת�ĵط��Ĵ���  
            	Intent tologin = new Intent(StartShow.this,MainActivity.class);
    			startActivity(tologin);
    			StartShow.this.finish();
                finish();  
            }    
        }, 2000);
		
			
		
	}
	public void Back(View view){
		super.onBackPressed();
	}
	
}

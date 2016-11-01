package com.example.app_test;




import GifView.GifView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

public class StartShow extends Activity{
	

	private GifView gifshow ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.startshow);
		gifshow = (GifView)findViewById(R.id.gif);
		gifshow.setMovieResource(R.drawable.startshow);

		new Handler().postDelayed(new Runnable() {    
            public void run() {   
                //你需要跳转的地方的代码  
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

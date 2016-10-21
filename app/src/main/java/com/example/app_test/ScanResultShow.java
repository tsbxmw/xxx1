package com.example.app_test;

import com.google.zxing.WriterException;
import com.google.zxing.client.android.BitmapUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ScanResultShow extends Activity {

	private TextView resultscan ;
	private Button buttonback;
	private ImageView scanimg;
	private String scanresult;
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_result_show);
		resultscan = (TextView)	findViewById(R.id.scanresult);
		buttonback = (Button) findViewById(R.id.scan_view_button_back);
		scanimg = (ImageView)findViewById(R.id.imageview);
		Intent intent  = this.getIntent();
		bundle = intent.getExtras();
		Scanresult_get();
		Create2QR();
		
	}
	public void Scanresult_get(){
		
		scanresult= bundle.getString("scanresult");
		System.out.println("debug : scanresult = " + scanresult);
		String userString= bundle.getString("user");
		System.out.println(userString);
		this.resultscan.setText(scanresult);
	}
	public void Create2QR(){
		 DisplayMetrics dm = new DisplayMetrics();
		 getWindowManager().getDefaultDisplay().getMetrics(dm);  
		 int mScreenWidth = dm.widthPixels;
		//      Bitmap bitmap = BitmapUtil.create2DCoderBitmap(uri, mScreenWidth/2, mScreenWidth/2);
		 Bitmap bitmap;
				try	{
					bitmap = BitmapUtil.createQRCode(scanresult,mScreenWidth);
					if(bitmap != null){
						scanimg.setImageBitmap(bitmap);
					}
					}catch (WriterException e){
			// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}

	public void ButtonBack(View view){
		Intent intent = new Intent();
		intent.setClass(ScanResultShow.this, test_main.class);
		
		intent.putExtras(bundle);
		startActivity(intent);
		this.finish();
	}
}

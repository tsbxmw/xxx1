package com.example.app_test;

import com.google.zxing.WriterException;
import com.google.zxing.client.android.BitmapUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendInfomation extends Activity{
	private Bundle bundle ;
	private String friend_name ;
	private ImageView imageview;
	private TextView textview,userinfo;
	private ImageView bitmap_create = null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.friend_info);
		imageview = (ImageView)findViewById(R.id.imageview);
		textview = (TextView)findViewById(R.id.user_name);
		userinfo =(TextView)findViewById(R.id.text_view);
		bitmap_create = (ImageView)findViewById(R.id.bitmap);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		friend_name = bundle.getString("friend");
		System.out.println("friends is " + friend_name);
		
		
		textview.setText(friend_name);
		userinfo.setText(friend_name);
		Create2QR();
		
	}
	
	public void Create2QR(){
		 
		 
		//      Bitmap bitmap = BitmapUtil.create2DCoderBitmap(uri, mScreenWidth/2, mScreenWidth/2);
		 Bitmap bitmap;
				try	{
					bitmap = BitmapUtil.createQRCode(friend_name, 1600);
					if(bitmap != null){
						bitmap_create.setImageBitmap(bitmap);
					}
					}catch (WriterException e){
			// TODO Auto-generated catch block
				e.printStackTrace();
				}
	}
	    
}

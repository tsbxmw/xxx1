package com.example.app_test;

import com.google.zxing.WriterException;
import com.google.zxing.client.android.BitmapUtil;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginSuccess_fragment_info  extends Fragment  {

	private TextView textview = null;
	private ImageView bitmap_create = null;
	private  View view = null;
	private TextView user = null;
	private Bundle bundle = null;
	private String username,city;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
	       	view = inflater.inflate(R.layout.fragment_main, container, false);  
	       	user = (TextView )view.findViewById(R.id.user_name);
	        textview = (TextView )view.findViewById(R.id.text_view);
	        bitmap_create = (ImageView)view.findViewById(R.id.bitmap);
	        
	        bundle = getArguments();
	        username  = bundle.getString("user");
	        try {
	        	city=bundle.getString("city");
	        	System.out.println("debug info fragement info get city name :"+city);
	        }catch (Exception e){
	        	e.printStackTrace();
	        }
	        if(city==null)
	        	city = "上海";
	        user.setText(username);
	        textview.setText(" city : "+city+" \n age : 18 \n sex : boy \n school : dut \n birth : 1206 \n");
	        Create2QR();
	        return view ;   
	    } 
	 
		public void Create2QR(){
				 
			 
			//      Bitmap bitmap = BitmapUtil.create2DCoderBitmap(uri, mScreenWidth/2, mScreenWidth/2);
			 Bitmap bitmap;
					try	{
						bitmap = BitmapUtil.createQRCode(username, 1600);
						if(bitmap != null){
							bitmap_create.setImageBitmap(bitmap);
						}
						}catch (WriterException e){
				// TODO Auto-generated catch block
					e.printStackTrace();
					}
		}
}

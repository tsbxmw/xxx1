package com.example.app_test;

import com.google.zxing.WriterException;
import com.google.zxing.client.android.BitmapUtil;

import ServerConnect.GetInfo;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SuppressLint("ResourceAsColor")
public class LoginSuccess_fragment_friends  extends Fragment  {

	
	
	private  View view = null;
	private TextView friend1 = null;
	private GetInfo getInfo = new GetInfo();
	private LinearLayout friends_list_layout ;
	private Bundle bundle;
	private Handler myHandler ;
	private String user;
	private int flag;
	private String [] friends = new String [1000];
	private TextView test_view = null;
	private Context test;
	private LinearLayout linearlayout;
	private ImageView image;
	
	 
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
		
	       	view = inflater.inflate(R.layout.fragment_friends, container, false); 
	       	friends_list_layout = (LinearLayout) view.findViewById(R.id.friends_list);
	       	friend1 = (TextView)view.findViewById(R.id.user_name);
	       	linearlayout = (LinearLayout)view.findViewById(R.id.linear_layout);
	       	image = (ImageView )view.findViewById(R.id.imageview);
	    	test = view.getContext();
	    
	       	bundle = getArguments();
			user = bundle.getString("user");
			Message message = new Message();
			message.what = 0;
			
			myHandler = new Handler() {  
		          public void handleMessage(Message msg) {   
		               switch (msg.what) {   
		                    case 0:   
		                         GetFriends(); 
		                         break;   
		                    case 1:
		                    	 ShowFriends();
		               }   
		               super.handleMessage(msg);   
		          }

				

				
		     };  
		     myHandler.sendMessage(message);
	        return view ;   
	    } 
	 public void GetFriends(){
	
		 Thread start = new Thread(){
				public void run(){
						flag = 0;
						Thread thread = new Thread(){
							public void run(){
								friends = getInfo.get_friends_all(user);	
								flag = 1;
							}
						};
						thread.start();
						while(flag == 0){
							
						}
						Message message = new Message();
						message.what = 1;
						myHandler.sendMessage(message);
						}
					};
		 start.start();
							
	 }
	 private void ShowFriends() {
			// TODO Auto-generated method stub
		 friend1.setText(user);
         List<String> list = new LinkedList<String>();
         for(int i = 0; i < friends.length; i++) {
             if(!list.contains(friends[i])) {
                 list.add(friends[i]);
             }
         }
         int size = list.size();
         friends = (String[])list.toArray(new String[size]);
         for(int i = 0 ;i<friends.length&&friends[i]!=null;i++){
			 System.out.println(friends[i]);
			 final String friend_now = friends[i];
			 LinearLayout linear = new LinearLayout(test);
			 linear.setLayoutParams(linearlayout.getLayoutParams());
			 LinearLayout.LayoutParams lyp = (LayoutParams) linearlayout.getLayoutParams();
			 lyp.setMargins(1, 10, 0, 0);
			 
			 linear.setLayoutParams(lyp);
			 linear.setBackgroundColor(R.color.beige);
			 ImageView imageView = new ImageView(test);
			 imageView.setLayoutParams(image.getLayoutParams());
			 imageView.setBackgroundResource(R.drawable.ic_launcher);
			 imageView.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("debug : this view v"
							+ "haha");
					 bundle.putString("friend", friend_now);
					 Intent intent = new Intent();
					 intent.setClass(getActivity(), FriendInfomation.class);
					 intent.putExtras(bundle);
					 if(getActivity()!=null){
						 getActivity().startActivity(intent);
					
				}
				}
			 }
				 
			 );
			 TextView textview = new TextView(test);
			 textview.setLayoutParams(friend1.getLayoutParams());
			 textview.setText(friends[i]);
			 textview.setTextColor(R.color.white);
			 
			 textview.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						System.out.println("debug : this view v"
								+ "haha");
						 bundle.putString("friend", friend_now);
						 Intent intent = new Intent();
						 intent.setClass(getActivity(), talk_to_friend.class);
						 intent.putExtras(bundle);
						 if(getActivity()!=null){
							 getActivity().startActivity(intent);
						
					}
					}
				 }
					 
				 );
			 linear.addView(imageView);
			 linear.addView(textview);
			 friends_list_layout.addView(linear);
			 
		 }
			
		}
		
}

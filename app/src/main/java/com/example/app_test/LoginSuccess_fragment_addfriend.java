package com.example.app_test;
//need a new add friends activity // TODO: 2016/11/8  
import com.example.app_test.R.color;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class LoginSuccess_fragment_addfriend  extends Fragment  {

	private Button button ;
	private Bundle bundle;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
		 	
	        View view = inflater.inflate(R.layout.fragment_addfriend, container, false);  
	        button = (Button) view.findViewById(R.id.add_friend);
	        bundle = getArguments();
	        OnClickListener scan = null;
	        scan = new OnClickListener() {
	            public void onClick(View v) {
	                System.out.println("xxxxxxxxxx");
	                scanclick(getView());
	            }
	        };
	       button.setOnClickListener(scan);
	        return view ;   
	    } 
	 public void scanclick(View view){
		 Intent intent = new Intent();
		 intent.setClass(getActivity(), AddFriend.class);
		 intent.putExtras(bundle);
		 if(getActivity()!=null){
			 getActivity().startActivity(intent);
			 
		 }
	 }
	
}

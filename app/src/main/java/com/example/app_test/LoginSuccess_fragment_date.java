package com.example.app_test;

import java.util.Calendar;
import java.util.Date;

import com.example.app_test.CalendarView.OnItemClickListener;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LoginSuccess_fragment_date  extends Fragment   implements View.OnTouchListener {

	private   View view  = null;
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
		 	view = inflater.inflate(R.layout.fragment_date, container, false);  
	       
	        return view ;   
	    }

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	 
	 
	 
	 
	 
	 
	 
	 

	 
}

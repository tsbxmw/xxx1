package com.example.app_test;

import com.example.app_test.R.color;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class LoginSuccess_fragment_Scan  extends Fragment  {

	private Button button ;
	private Bundle bundle;
	private WebView webView;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
		 	
	        View view = inflater.inflate(R.layout.scan_bitmap, container, false);  
	        button = (Button) view.findViewById(R.id.scan_button);
			webView = (WebView)view.findViewById(R.id.showweb) ;
	        bundle = getArguments();

			webView.loadUrl("https://github.com/tsbxmw/xxx1");
			WebSettings settings = webView.getSettings();
			settings.setJavaScriptEnabled(true);
			webView.setWebViewClient(new WebViewClient(){
				@Override
				public boolean shouldOverrideUrlLoading(WebView view,String url){
					view.loadUrl(url);
					return true;
				}
			});
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
		 intent.setClass(getActivity(), com.google.zxing.client.android.CaptureActivity.class);
		 intent.putExtras(bundle);
		 if(getActivity()!=null){
			 getActivity().startActivity(intent);
			 
		 }
	 }
	
}

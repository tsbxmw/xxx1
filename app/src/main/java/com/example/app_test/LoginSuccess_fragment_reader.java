package com.example.app_test;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

;

public class LoginSuccess_fragment_reader extends Fragment  {



	private  View view = null;


    private WebView webView;

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
	       	view = inflater.inflate(R.layout.fragment_reader, container, false);
            webView = (WebView)view.findViewById(R.id.info);
            webView.loadUrl("http://dushu.m.baidu.com");
            WebSettings settings;
            settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view,String url){
                    view.loadUrl(url);
                    return true;
                }
            });

	        return view;
	    } 
	 


}

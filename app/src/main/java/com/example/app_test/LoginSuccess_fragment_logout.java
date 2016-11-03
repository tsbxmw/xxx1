package com.example.app_test;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

;import static android.content.Context.MODE_PRIVATE;

public class LoginSuccess_fragment_logout extends Fragment  {



	private  View view = null;


    private WebView webView;
    private Bundle bundle = null;

	 public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState)  
	    {  
	       	view = inflater.inflate(R.layout.fragment_logout, container, false);
            webView = (WebView)view.findViewById(R.id.info);
            bundle = getArguments();
            webView.loadUrl("http://tsbxapptest.applinzi.com/Qrcode?keycode_other=byebye");
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


            new AlertDialog.Builder(getActivity()).setTitle("EXIT")
                    .setMessage("You Sure for EXIT?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            Context context = getActivity();
                            SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putBoolean("autologin",false);
                            editor.commit();
                            Intent back = new Intent(getActivity(),MainActivity.class);
                            startActivity(back);
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getActivity(),test_main.class);
                            intent.putExtras(bundle);
                            if(getActivity()!=null) {
                                getActivity().startActivity(intent);
                            }

                        }
                    })
                    .show();
	        return view;
	    } 
	 


}

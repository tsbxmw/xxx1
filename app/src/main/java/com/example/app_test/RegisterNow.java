package com.example.app_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterNow extends Activity{
	private EditText user = null;
	private EditText pass = null;
	private EditText pass_again = null;
	private Button button_register = null;
	//private String username,password,passwordagain;
	private String username;
	private String user_city = "shanghai";
	private String user_get,pass_get,pass_again_get,result;
	

	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		user = (EditText )findViewById (R.id.user_new);
		pass = (EditText)findViewById(R.id.pass_new);
		pass_again = (EditText)findViewById(R.id.pass_again);
		button_register = (Button)findViewById(R.id.button_register);
		user.addTextChangedListener(Watchertext);
		
	}

	TextWatcher Watchertext = new TextWatcher() {
		private CharSequence temp;
		private int editStart;
		private int editEnd;
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//user.setText(s);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			temp =s;
		}

		@Override
		public void afterTextChanged(Editable s) {
			editStart = user.getSelectionStart();
			editEnd = user.getSelectionEnd();
            if(!isLetterOrDigit(temp.toString())){

                Toast.makeText(RegisterNow.this,"username is Letter or Digit or _ !",Toast.LENGTH_SHORT).show();

            }
			if(temp.length() > 10)
			{
				Toast.makeText(RegisterNow.this,"too long for user name !",Toast.LENGTH_SHORT).show();
				s.delete(editStart-1,editEnd);
				int tempSeleciton = editStart;
				user.setSelection(tempSeleciton);
			}
		}
	};
    public static boolean isLetterOrDigit(String str) {
        boolean isLetterOrDigit = false;//定义一个boolean值，用来表示是否包含字母或数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetterOrDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }
	public void RegisterNow(View view){
		
		
		user_get = user.getText().toString();
		pass_get = pass.getText().toString();

		pass_again_get = pass_again.getText().toString();
		Bundle bundle = new Bundle();
		bundle.putString("user_get", user_get);
		bundle.putString("pass_get", pass_get);
		bundle.putString("pass_again_get", pass_again_get);
		Intent toregistersuccess = new Intent(RegisterNow.this,RegisterShow.class);
		toregistersuccess.putExtras(bundle);
		startActivity(toregistersuccess);
		RegisterNow.this.finish();
		
	
	}
	public void Back(View view){
		super.onBackPressed();
		this.finish();
	}
	
	
}

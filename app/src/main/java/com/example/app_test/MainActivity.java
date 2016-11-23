package com.example.app_test;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends Activity {

	private EditText user = null;
	private EditText pass = null;
	private Button button_back = null;
	private Button button_login = null;
	private Button button_register = null;
	private CheckBox remember = null;
	private CheckBox autologin = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		
		//setContentView(R.layout.activity_login);
		button_back = (Button)findViewById(R.id.button_back);
		button_login = (Button)findViewById(R.id.button_login);
		button_register = (Button)findViewById(R.id.button_register);
		user =(EditText)findViewById(R.id.user);
		pass = (EditText)findViewById(R.id.pass);
		remember = (CheckBox)findViewById(R.id.remember);
		autologin = (CheckBox)findViewById(R.id.autologin);
		Context context = MainActivity.this;
		SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
		String user_from_file = sp.getString("user", "");
		String pass_from_file = sp.getString("pass", "");
		System.out.println(user_from_file);
		user.addTextChangedListener(Watchertext);
		boolean passremberb = sp.getBoolean("remember",false);
		boolean autologinb = sp.getBoolean("autologin",false);
		if(user_from_file!=null && passremberb)
			user.setText(user_from_file);
		user.setText(user_from_file);
		if(pass_from_file!=null && passremberb) {
			pass.setText(pass_from_file);

			if(autologinb)
				autologin.setChecked(true);
		}

		if(passremberb)
			remember.setChecked(true);
		if(autologinb){
			LoginNow(button_login);
		}
		
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
				Toast.makeText(MainActivity.this,"username is Letter or Digit or _ !",Toast.LENGTH_SHORT).show();

			}
			if(temp.length() > 10)
			{
				Toast.makeText(MainActivity.this,"too long for user name !",Toast.LENGTH_SHORT).show();
				s.delete(editStart-1,editEnd);
				int tempSeleciton = editStart;
				user.setSelection(tempSeleciton);
			}
		}
	};
	
	public void LoginNow(final View view){
		String userString = user.getText().toString();
		String passString = pass.getText().toString();

		if(isLetterOrDigit(userString))
			System.out.println();
		else {
			Intent intent = new Intent(MainActivity.this,MainActivity.class);
			startActivity(intent);
			this.finish();
		}
		if(isLetterDigit(passString))
			System.out.println();
		else{
			Intent intent = new Intent(MainActivity.this,MainActivity.class);
			startActivity(intent);
			this.finish();
		}


		Context context = MainActivity.this;
	    SharedPreferences sp = context.getSharedPreferences("Login", MODE_PRIVATE);
		Editor editor = sp.edit();
		if(remember.isChecked())
			editor.putBoolean("remember",true);
		else
			editor.putBoolean("remember",false);
		if(autologin.isChecked())
			editor.putBoolean("autologin",true);
		else
			editor.putBoolean("autologin",false);
		editor.putString("user", userString);
		editor.putString("pass",passString);

		editor.commit();
		Bundle bundle = new Bundle();
		Intent toRegister = new Intent(MainActivity.this,LoginNow.class);
		bundle.putString("user", userString);
		bundle.putString("pass", passString);
		toRegister.putExtras(bundle);
		startActivity(toRegister); 
		this.finish();
	}
	public void RegisterNow(View view){
		
		Intent toRegister = new Intent(MainActivity.this,RegisterNow.class);
		startActivity(toRegister); 
		
	}
	public void Back(View view){
		super.onBackPressed();
		this.finish();
	}



	/**
	 * 规则1：至少包含大小写字母及数字中的一种
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
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

	/**
	 * 规则2：至少包含大小写字母及数字中的两种
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigit(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLetter = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLetter && str.matches(regex);
		return isRight;
	}

	/**
	 * 规则3：必须同时包含大小写字母及数字
	 * 是否包含
	 *
	 * @param str
	 * @return
	 */
	public static boolean isContainAll(String str) {
		boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
		boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
		boolean isUpperCase = false;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
				isDigit = true;
			} else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
				isLowerCase = true;
			} else if (Character.isUpperCase(str.charAt(i))) {
				isUpperCase = true;
			}
		}
		String regex = "^[a-zA-Z0-9]+$";
		boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
		return isRight;
	}

	/**
	 *   判断EditText输入的数字、中文还是字母方法
	 */
	public static void whatIsInput(Context context, EditText edInput) {
		String txt = edInput.getText().toString();

		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(txt);
		if (m.matches()) {
			Toast.makeText(context, "输入的是数字", Toast.LENGTH_SHORT).show();
		}
		p = Pattern.compile("[a-zA-Z]");
		m = p.matcher(txt);
		if (m.matches()) {
			Toast.makeText(context, "输入的是字母", Toast.LENGTH_SHORT).show();
		}
		p = Pattern.compile("[\u4e00-\u9fa5]");
		m = p.matcher(txt);
		if (m.matches()) {
			Toast.makeText(context, "输入的是汉字", Toast.LENGTH_SHORT).show();
		}
	}
}


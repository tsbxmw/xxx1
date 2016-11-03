package com.example.app_test;

import android.app.Activity;

import com.example.app_test.*;

import ServerConnect.GetInfo;
import ServerConnect.GetWeather;
import ServerConnect.QueryTask;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class test_main extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
/*
modify : mengwei remove the login weather show , replaced by login weather new!
*/

	//private LoginSuccess_fragment_weather login_w = new LoginSuccess_fragment_weather();
	private LoginSuccess_fragment_weather_new login_w_n = new LoginSuccess_fragment_weather_new();
	private LoginSuccess_fragment_Scan  login_s = new LoginSuccess_fragment_Scan ();
	private LoginSuccess_fragment_info login_i = new LoginSuccess_fragment_info();
	private LoginSuccess_fragment_date login_d = new LoginSuccess_fragment_date();
	private LoginSuccess_fragment_friends login_fd = new LoginSuccess_fragment_friends();
	private LoginSuccess_fragment_addfriend login_adf = new LoginSuccess_fragment_addfriend();
	private LoginSuccess_fragment_reader login_reader = new LoginSuccess_fragment_reader();
	private LoginSuccess_fragment_logout logout = new LoginSuccess_fragment_logout();

	Bundle bundle;
	String userString;
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main);
		Intent intent = this.getIntent();
		bundle = intent.getExtras();
		//login_w.setArguments(bundle);
		login_w_n.setArguments(bundle);
		login_s.setArguments(bundle);
		login_i.setArguments(bundle);
		login_fd.setArguments(bundle);
		login_d.setArguments(bundle);
		login_adf.setArguments(bundle);
		login_reader.setArguments(bundle);
		logout.setArguments(bundle);
		userString = bundle.getString("user");
		System.out.println("debug :====="+userString);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		System.out.println(position);
		FragmentManager fragmentManager = getFragmentManager();
		switch (position) {
		case 0:
			fragmentManager.beginTransaction().replace(R.id.container, login_i).commit();
			break;
		case 1:
			//fragmentManager.beginTransaction().replace(R.id.container, login_w).commit();
			fragmentManager.beginTransaction().replace(R.id.container,login_w_n).commit();
			break;
		case 2:
			fragmentManager.beginTransaction().replace(R.id.container, login_s).commit();
			break;
		case 3:
			fragmentManager.beginTransaction().replace(R.id.container, login_d).commit();
			break;
		case 4:
			fragmentManager.beginTransaction().replace(R.id.container, login_fd).commit();
			break;
		case 5:
			fragmentManager.beginTransaction().replace(R.id.container, login_adf).commit();
			break;
		case 6:
			fragmentManager.beginTransaction().replace(R.id.container,login_reader).commit();
			break;
		case 7:
			fragmentManager.beginTransaction().replace(R.id.container,logout).commit();
			break;
		default:
			fragmentManager.beginTransaction().replace(R.id.container, login_i).commit();
			break;
		}
		
		
		
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section2);
			
			break;
		case 2:
			mTitle = getString(R.string.title_section1);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		case 4:
			mTitle = getString(R.string.title_section4);
			break;
		case 5:
			mTitle = getString(R.string.title_section5);
			break;
		case 6:
			mTitle = getString(R.string.title_section6);
			break;
		case 7 :
			mTitle = getString(R.string.title_section7);
			break;
		case 8:
			mTitle = getString(R.string.title_section8);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((test_main) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}


}

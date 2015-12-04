package com.procity.top;

import java.io.File;
import java.util.HashMap;

import com.procity.util.ActionEngine;
import com.procity.view.DonateFragment;
import com.procity.view.TheCityFragment;
import com.procity.view.ProfileFragment;
import com.procity.top.R;

import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;

/**
 * @author mwho
 * 
 */
public class ProcityActivity extends FragmentActivity implements
		TabHost.OnTabChangeListener {

	private static TabHost mTabHost;
	private static HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
	private static TabInfo mLastTab = null;

	public static EditText post;
	public static String username;
	public static String propoints;
	public static String preflocation;
	public static String rank;

	
	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Step 1: Inflate layout
		setContentView(R.layout.tabs_layout);
		// Step 2: Setup TabHost
		initialiseTabHost(savedInstanceState);
		if (savedInstanceState != null) {
			mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); 
		}

	}

	@Override
	public void onBackPressed() {

		File f = getBaseContext().getFileStreamPath("savelogin");

		if (f.exists()) {

			Intent i = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(i);

		} else {

			Intent i = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(i);

		}

		super.onBackPressed();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab
																// selected
		super.onSaveInstanceState(outState);
	}

	/**
	 * Step 2: Setup TabHost
	 */
	private void initialiseTabHost(Bundle args) {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		TabInfo tabInfo = null;
		ProcityActivity.addTab(this, mTabHost,
				mTabHost.newTabSpec("Tab1").setIndicator("The City"),
				(tabInfo = new TabInfo("Tab1", TheCityFragment.class, args)));
		mapTabInfo.put(tabInfo.tag, tabInfo);
		ProcityActivity.addTab(this, mTabHost,
				mTabHost.newTabSpec("Tab2").setIndicator("Donate"),
				(tabInfo = new TabInfo("Tab2", DonateFragment.class, args)));
		mapTabInfo.put(tabInfo.tag, tabInfo);
		ProcityActivity.addTab(this, mTabHost,
				mTabHost.newTabSpec("Tab3").setIndicator("Profile"),
				(tabInfo = new TabInfo("Tab3", ProfileFragment.class, args)));
		mapTabInfo.put(tabInfo.tag, tabInfo);
		// Default to first tab
		this.onTabChanged("Tab1");
		//
		mTabHost.setOnTabChangedListener(this);
	}

	/**
	 * @param activity
	 * @param tabHost
	 * @param tabSpec
	 * @param clss
	 * @param args
	 */
	private static void addTab(ProcityActivity activity, TabHost tabHost,
			TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		// Attach a Tab view factory to the spec
		tabSpec.setContent(activity.new TabFactory(activity));
		String tag = tabSpec.getTag();

		// Check to see if we already have a fragment for this tab, probably
		// from a previously saved state. If so, deactivate it, because our
		// initial state is that a tab isn't shown.
		tabInfo.fragment = activity.getSupportFragmentManager()
				.findFragmentByTag(tag);
		if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
			FragmentTransaction ft = activity.getSupportFragmentManager()
					.beginTransaction();
			ft.detach(tabInfo.fragment);
			ft.commit();
			activity.getSupportFragmentManager().executePendingTransactions();
		}

		tabHost.addTab(tabSpec);
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see android.widget.TabHost.OnTabChangeListener#onTabChanged(java.lang.String)
	 */
	public void onTabChanged(String tag) {
		TabInfo newTab = mapTabInfo.get(tag);

		if (mLastTab != newTab) {
			FragmentTransaction ft = this.getSupportFragmentManager()
					.beginTransaction();
			if (mLastTab != null) {
				if (mLastTab.fragment != null) {
					ft.hide(mLastTab.fragment);
				}
			}
			if (newTab != null) {
				if (newTab.fragment == null) {
					newTab.fragment = Fragment.instantiate(this,
							newTab.clss.getName(), newTab.args);
					ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
					
				} else {

					if (tag.equals("Tab3")) {
						
						refreshProfile();
						
					}
					
					
					ft.show(newTab.fragment);
					
					// hide keyboard after posting.
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(mTabHost.getWindowToken(), 0);

				}
			}

			mLastTab = newTab;
			ft.commit();
			this.getSupportFragmentManager().executePendingTransactions();

		}
	}
	
	public void onRefreshProfile(View v) {

		refreshProfile();
		
	}

	
	private void refreshProfile() {
		

		if (!ActionEngine.haveNetworkConnection(this)) {

			Toast.makeText(getApplicationContext(),
					"Network Error - check connection", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ActionEngine.getProfile(this);
		
		TextView usr = (TextView) findViewById(R.id.profileusername);
		TextView pp = (TextView) findViewById(R.id.profilepropoints);
		TextView pf = (TextView) findViewById(R.id.profilepreferredloation);
		
		usr.setText(ProcityActivity.username);
		pp.setText(ProcityActivity.propoints);
		pf.setText(ProcityActivity.preflocation);
			
	}

	public void onRefresh(View v) {

		if (!ActionEngine.haveNetworkConnection(this)) {

			Toast.makeText(getApplicationContext(),
					"Login Error - check connection", Toast.LENGTH_SHORT).show();
			return;
		}
		
		ActionEngine.refreshFeed(this);
		
		
	}
	
	
	public void onGeneric(View v) {
			
		Intent i = new Intent(getApplicationContext(),
				GenericActivity.class);
	
		startActivity(i);
		
		
	}
	
	public void onUnique(View v) {
		
		
		Intent i = new Intent(getApplicationContext(),
				UniqueActivity.class);
	
		startActivity(i);
		
		
	}
	
	public void onClaimedItems(View v) {
		
		Intent i = new Intent(getApplicationContext(),
				ClaimedItemsActivity.class);
	
		startActivity(i);
		
		
	}
	
	public void onDonatedItems(View v) {
		
		Intent i = new Intent(getApplicationContext(),
				DonatedItemsActivity.class);
	
		startActivity(i);
		
		
	}

	@SuppressWarnings("deprecation")
	public void onSignOff(View v) {

		AlertDialog ad = new AlertDialog.Builder(this).create();
		ad.setCancelable(false); // This blocks the 'BACK' button
		ad.setMessage("Removing credentials - Confirm?");

		ad.setButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				File f = getBaseContext().getFileStreamPath("savelogin");
				f.delete();	
				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
			}
		});

		ad.setButton2("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		
		ad.show();
	}

	@SuppressWarnings("rawtypes")
	private class TabInfo {
		private String tag;
		private Class clss;
		private Bundle args;
		private Fragment fragment;

		TabInfo(String tag, Class clazz, Bundle args) {
			this.tag = tag;
			this.clss = clazz;
			this.args = args;
		}

	}

	class TabFactory implements TabContentFactory {

		private final Context mContext;

		/**
		 * @param context
		 */
		public TabFactory(Context context) {
			mContext = context;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		 */
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}

	}

}
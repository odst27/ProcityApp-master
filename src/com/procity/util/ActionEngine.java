package com.procity.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.procity.tasks.FetchClaimedItems;
import com.procity.tasks.FetchDonatedItems;
import com.procity.tasks.GenericDonateItemTask;
import com.procity.tasks.FetchTheCityTask;
import com.procity.tasks.FieldsTask;
import com.procity.tasks.ProfileTask;
import com.procity.tasks.RegisterTask;
import com.procity.tasks.UniqueDonateItemTask;
import com.procity.tasks.ValidateTask;
import com.procity.top.ClaimedItemsActivity;
import com.procity.top.DonatedItemsActivity;
import com.procity.top.GenericActivity;
import com.procity.top.LoginActivity;
import com.procity.top.ProcityActivity;

import com.procity.view.TheCityFragment;




import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import android.widget.Toast;

public class ActionEngine {
	
	
	public static String profile = "http://www.myprocity.com/android/profile.php";
	public static String fetchcity = "http://www.myprocity.com/android/fetchthecity.php";
	public static String validatelogin = "http://www.myprocity.com/android/signin.php";
	public static String registeruser = "http://www.myprocity.com/android/signup.php";
	public static String donategeneric = "http://www.myprocity.com/android/donategeneric.php";
	public static String donateunique = "http://www.myprocity.com/android/donateunique.php";
	public static String fetchfields = "http://www.myprocity.com/android/fetchfields.php";
	public static String fetchdonateditems = "http://www.myprocity.com/android/fetchdonateditems.php";
	public static String fetchclaimeditems = "http://www.myprocity.com/android/fetchclaimeditems.php";
	
	public static ProgressDialog pDialog;
	public static JSONParser jsonParser = new JSONParser();
	public static JSONObject json = new JSONObject();
	
	public static List<String> uniquecategory = new ArrayList<String>();
	public static ItemsRecord iRec;
	
	
	public static void saveCredentials(String name, Context c) {

		FileOutputStream fos;

		try {

			fos = c.openFileOutput("savelogin", Context.MODE_PRIVATE);
			fos.write(name.getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String readCredentials(Context c) {

		FileInputStream fin;

		try {

			String collected = null;
			fin = c.openFileInput("savelogin");
			byte[] dataArr = new byte[fin.available()];

			while (fin.read(dataArr) != -1) {

				collected = new String(dataArr);
				return collected;
			}

			fin.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "";
	}
	public static boolean haveNetworkConnection(Context c) {
		boolean haveConnectedWifi = false;
		boolean haveConnectedMobile = false;

		ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					haveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					haveConnectedMobile = true;
		}
		
		return haveConnectedWifi || haveConnectedMobile;
	}
	
	
	public static void refreshFeed(Activity act){
				
		FetchTheCityTask f = new FetchTheCityTask(act);
		f.execute();
	
		try {

			if (f.get() != null) {
				
				JSONArray feedArr = new JSONArray(ActionEngine.jsonParser.getJSONString());
				ArrayList<UserRecord> items = new ArrayList<UserRecord>();
					
				for(int i = 0; i < feedArr.length(); i++){
					
					JSONObject row = feedArr.getJSONObject(i);
					String item = row.getString("Item");
					String descp = row.getString("Description");
					String pic = row.getString("PicPath");
					String time = row.getString("Time");
					String donatedby = row.getString("DonatedBy");
					String ebase = row.getString("Ebase");
					String cond = row.getString("Condition");
					String loc = row.getString("Location");
					
					items.add(new UserRecord(item,descp,pic,time,donatedby,
							ebase,cond,loc));
										
				
 				}
												
				TheCityFragment.feedArea.setAdapter(new UserItemAdapter(act,
						android.R.layout.simple_list_item_1, items));
								
			} else {

				Toast.makeText(act.getApplicationContext(),
						"Feed did not update.", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		
	public static void registerUser(Activity act, View v){

		RegisterTask val = new RegisterTask(act);
		val.execute();

		try {

			String result = val.get();
			
			if (result.equals("1")) {

				Toast.makeText(act.getApplicationContext(),
						"Success! Check your email to confirm ",
						Toast.LENGTH_SHORT).show();

			} else if (result.equals("0")) {

				Toast.makeText(act.getApplicationContext(),
						"Username taken", Toast.LENGTH_LONG)
						.show();

			} else if (result.equals("-2")) {

				Toast.makeText(act.getApplicationContext(),
						"Email taken", Toast.LENGTH_LONG)
						.show();

			} else {
				
				Toast.makeText(act.getApplicationContext(),
						result, Toast.LENGTH_LONG)
						.show();
				
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public static void validateUser(Activity act, View v){

		ValidateTask val = new ValidateTask(act);
		val.execute();

		try {

			String result = val.get();
			if (result.equals("1")) {

				ProcityActivity.username = LoginActivity.username.getText().toString();
				
				// hide keyboard after posting.
				InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

				if (LoginActivity.chBox.isChecked()) {

					ActionEngine.saveCredentials(ProcityActivity.username, act);

				}
				
				// successfully created product
				Intent i = new Intent(act.getApplicationContext(),
						ProcityActivity.class);

				act.startActivity(i);
				act.finish();

				Toast.makeText(act.getApplicationContext(),
						"Welcome to Procity " + ProcityActivity.username.toString() + "!",
						Toast.LENGTH_SHORT).show();

			} else if (result.equals("0")) {

				Toast.makeText(act.getApplicationContext(),
						"Login Error - check credentials", Toast.LENGTH_LONG)
						.show();

			} else if (result.equals("-1")) {

				Toast.makeText(act.getApplicationContext(),
						"Login Error - database error", Toast.LENGTH_LONG)
						.show();

			} else if (result.equals("-2")) {

				Toast.makeText(act.getApplicationContext(),
						"Login Error - internal error", Toast.LENGTH_LONG)
						.show();

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void getProfile(Activity act) {
		
		ProfileTask f = new ProfileTask(act);
		f.execute();
	
		try {

			if (f.get() != null) {
								
				try {
					ProcityActivity.username = (String) ActionEngine.json.get("username");
					ProcityActivity.propoints = (String) ActionEngine.json.get("propoints");
					ProcityActivity.preflocation = (String) ActionEngine.json.get("preflocation");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
										
			} else {

				Toast.makeText(act.getApplicationContext(),
						"Profile did not update.", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public static void processGeneric(GenericActivity act, View v) {
		
		GenericDonateItemTask f = new GenericDonateItemTask(act);
		f.execute();
	
		try {

			String result = f.get();
			if (result.equals("1")) {

			
				// successfully created product
				/*Intent i = new Intent(act.getApplicationContext(),
						ProcityActivity.class);

				act.startActivity(i);
				act.finish();*/

				Toast.makeText(act.getApplicationContext(),
						"Thanks for your donation " + ProcityActivity.username.toString() + "!",
						Toast.LENGTH_SHORT).show();

			} else if (result.equals("-2")) {

				Toast.makeText(act.getApplicationContext(),
						"Max 10 items per person", Toast.LENGTH_LONG)
						.show();

			} else if (result.equals("-3")) {

				try {
					String show = ActionEngine.json.getString("pic");
					Toast.makeText(act.getApplicationContext(),
							show, Toast.LENGTH_LONG)
							.show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;

			} else if (result.equals("-1")) {

				Toast.makeText(act.getApplicationContext(),
						"File too big", Toast.LENGTH_LONG)
						.show();

			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public static void getFields(Activity act) {
		
		FieldsTask f = new FieldsTask(act);
		f.execute();
	
		try {

			if (f.get() != null) {
								
				
				JSONArray feedArr = new JSONArray(ActionEngine.jsonParser.getJSONString());
				iRec = new ItemsRecord();
				
				for(int i = 0; i < feedArr.length(); i++){
					
					JSONObject row = feedArr.getJSONObject(i);
					String cat = row.getString("Category");
					String subcat = row.getString("SubCategory");
					String item = row.getString("Item");
					String emin = row.getString("Emin");
					String emed = row.getString("Emed");
					String emax = row.getString("Emax");
					
					String[] valsarr = new String[3];
					valsarr[0] = emin;
					valsarr[1] = emed;
					valsarr[2] = emax;
					
					if(iRec.containsCategory(cat)){
												
				    	if(iRec.containsSubcat(cat,subcat)){
				    			
				    		iRec.addNewItem(cat,subcat, item, valsarr);
				    	
						} else {
							
							iRec.addNewSubCat(cat, subcat);
							iRec.addNewItem(cat,subcat, item, valsarr);
							
						}
						
					}else {
						
						iRec.addNewCategory(cat);
						iRec.addNewSubCat(cat, subcat);
						iRec.addNewItem(cat,subcat, item, valsarr);
					}
				
 				}
				

										
			} else {

				Toast.makeText(act.getApplicationContext(),
						"Profile did not update.", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void processUnique(Activity act, View v) {
		
		UniqueDonateItemTask f = new UniqueDonateItemTask(act);
		f.execute();
	
		try {

			String result = f.get();
			if (result.equals("1")) {

			
				// successfully created product
				/*Intent i = new Intent(act.getApplicationContext(),
						ProcityActivity.class);

				act.startActivity(i);
				act.finish();*/

				Toast.makeText(act.getApplicationContext(),
						"Thanks for your donation " + ProcityActivity.username.toString() + "!",
						Toast.LENGTH_SHORT).show();

			} else if (result.equals("-2")) {

				Toast.makeText(act.getApplicationContext(),
						"Max 10 items per person", Toast.LENGTH_LONG)
						.show();

			} else if (result.equals("-3")) {

				try {
					String show = ActionEngine.json.getString("pic");
					Toast.makeText(act.getApplicationContext(),
							show, Toast.LENGTH_LONG)
							.show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;

			} else if (result.equals("-1")) {

				Toast.makeText(act.getApplicationContext(),
						"File too big", Toast.LENGTH_LONG)
						.show();

			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public static void refreshDonatedItems(
			Activity act) {
		
		FetchDonatedItems f = new FetchDonatedItems(act);
		f.execute();
	
		try {

			if (f.get() != null) {
				
				JSONArray feedArr = new JSONArray(ActionEngine.jsonParser.getJSONString());
				ArrayList<UserRecord> items = new ArrayList<UserRecord>();
					
				for(int i = 0; i < feedArr.length(); i++){
					
					JSONObject row = feedArr.getJSONObject(i);
					String item = row.getString("Item");
					String descp = row.getString("Description");
					String pic = row.getString("PicPath");
					String time = row.getString("Time");
					String donatedby = row.getString("DonatedBy");
					String ebase = row.getString("Ebase");
					String cond = row.getString("Condition");
					String loc = row.getString("Location");
					
					items.add(new UserRecord(item,descp,pic,time,donatedby,
							ebase,cond,loc));
										
				
 				}
												
				DonatedItemsActivity.donatefeedArea.setAdapter(new UserItemAdapter(act,
						android.R.layout.simple_list_item_1, items));
								
			} else {

				Toast.makeText(act.getApplicationContext(),
						"Feed did not update.", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	public static void refreshClaimedItems(
			Activity act) {
		
		FetchClaimedItems f = new FetchClaimedItems(act);
		f.execute();
	
		try {

			if (f.get() != null) {
				
				JSONArray feedArr = new JSONArray(ActionEngine.jsonParser.getJSONString());
				ArrayList<UserRecord> items = new ArrayList<UserRecord>();
					
				for(int i = 0; i < feedArr.length(); i++){
					
					JSONObject row = feedArr.getJSONObject(i);
					String item = row.getString("Item");
					String descp = row.getString("Description");
					String pic = row.getString("PicPath");
					String time = row.getString("Time");
					String donatedby = row.getString("DonatedBy");
					String ebase = row.getString("Ebase");
					String cond = row.getString("Condition");
					String loc = row.getString("Location");
					
					items.add(new UserRecord(item,descp,pic,time,donatedby,
							ebase,cond,loc));
										
				
 				}
												
				ClaimedItemsActivity.claimedfeedArea.setAdapter(new UserItemAdapter(act,
						android.R.layout.simple_list_item_1, items));
								
			} else {

				Toast.makeText(act.getApplicationContext(),
						"Feed did not update.", Toast.LENGTH_SHORT)
						.show();
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}

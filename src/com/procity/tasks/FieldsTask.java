package com.procity.tasks;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.procity.top.ProcityActivity;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class FieldsTask extends AsyncTask<String, String, String> {

	private Activity thisAct;
	
	
	public FieldsTask(Activity act){		
		super();
		thisAct = act;
	}
	
	@Override
	public void onPreExecute() {

		super.onPreExecute();
		ActionEngine.pDialog = new ProgressDialog(thisAct);
		ActionEngine.pDialog.setMessage("Processing...");
		ActionEngine.pDialog.show();
		
		
	}
	
	@Override
	public String doInBackground(String... arg0) {
		
		String username = ProcityActivity.username;
		
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("Username", username));
		params.add(new BasicNameValuePair("Mobile", "android"));
		
		ActionEngine.json = ActionEngine.jsonParser.makeHttpRequest(ActionEngine.fetchfields,
				"POST", params);

		try {
			
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
		
	}

	// dismiss the dialog once done
	
	@Override
	public void onPostExecute(String result) {

		super.onPostExecute(result);
		ActionEngine.pDialog.dismiss();
	}
	
}
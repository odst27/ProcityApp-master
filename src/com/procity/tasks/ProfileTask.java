package com.procity.tasks;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.procity.top.ProcityActivity;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class ProfileTask extends AsyncTask<String, String, String> {

	private Activity thisAct;
	
	public ProfileTask(Activity act){		
		super();
		thisAct = act;
	}
	
	/**
	 * Before starting background thread Show Progress Dialog
	 * */
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		ActionEngine.pDialog = new ProgressDialog(thisAct);
		ActionEngine.pDialog.setMessage("Refreshing profile...");
		ActionEngine.pDialog.setIndeterminate(false);
		ActionEngine.pDialog.setCancelable(true);
		ActionEngine.pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {

		String user = ProcityActivity.username;
	
		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("Username", user));
		
		try {

			ActionEngine.json = ActionEngine.jsonParser.makeHttpRequest(
					ActionEngine.profile, "POST", params);

			return ActionEngine.json.toString();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";

	}

	protected void onPostExecute(String file_url) {
		// dismiss the dialog once done
		ActionEngine.pDialog.dismiss();

	}

}

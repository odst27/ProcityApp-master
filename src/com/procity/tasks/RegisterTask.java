package com.procity.tasks;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.procity.top.RegisterActivity;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class RegisterTask extends AsyncTask<String, String, String> {

	private Activity thisAct;
	
	public RegisterTask(Activity act){		
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
		ActionEngine.pDialog.setMessage("Registering user...");
		ActionEngine.pDialog.setIndeterminate(false);
		ActionEngine.pDialog.setCancelable(true);
		ActionEngine.pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {

		String user = RegisterActivity.username.getText().toString();
		String pass = RegisterActivity.password.getText().toString();
		String email = RegisterActivity.email.getText().toString();

		ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("Username", user));
		params.add(new BasicNameValuePair("Password", pass));
		params.add(new BasicNameValuePair("Email", email));
		params.add(new BasicNameValuePair("Mobile", "android"));

		try {

			JSONObject json = ActionEngine.jsonParser.makeHttpRequest(
					ActionEngine.registeruser, "POST", params);

			return json.getString("success");

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

package com.procity.tasks;


import java.io.File;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.procity.top.GenericActivity;
import com.procity.top.ProcityActivity;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GenericDonateItemTask extends AsyncTask<String, String, String> {

	private Activity thisAct;

	public GenericDonateItemTask(Activity act){		
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
		
		String catchoice = GenericActivity.categorychoice;
		String subchoice = GenericActivity.subcategorychoice;
		String itemchoice = GenericActivity.itemchoice;
		File image = GenericActivity.myFile;
		String descp = GenericActivity.descp.getText().toString();
		String username = ProcityActivity.username.toString();
		
		MultipartEntity params = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		try {
			
			params.addPart("username", new StringBody(username));
			params.addPart("category", new StringBody(catchoice));
			params.addPart("sub_category", new StringBody(subchoice));
			params.addPart("item", new StringBody(itemchoice));
			params.addPart("descp", new StringBody(descp));
			params.addPart("image", new FileBody(image));
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		ActionEngine.json = ActionEngine.jsonParser.makeHttpFileRequest(ActionEngine.donategeneric,
				"POST", params);

		try {
			
			return ActionEngine.json.getString("success");

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
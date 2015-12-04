package com.procity.tasks;


import java.io.File;
import java.io.UnsupportedEncodingException;


import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import com.procity.top.ProcityActivity;
import com.procity.top.UniqueActivity;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class UniqueDonateItemTask extends AsyncTask<String, String, String> {

	private Activity thisAct;

	public UniqueDonateItemTask(Activity act){		
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
		
		String catchoice = UniqueActivity.uniquecategorychoice;
		String itemchoice = UniqueActivity.itemchoice;
		File image = UniqueActivity.unimyFile;
		String descp = UniqueActivity.unidescp.getText().toString();
		String username = ProcityActivity.username.toString();
		
		MultipartEntity params = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		
		try {
			
			params.addPart("username", new StringBody(username));
			params.addPart("category", new StringBody(catchoice));
			params.addPart("item", new StringBody(itemchoice));
			params.addPart("descp", new StringBody(descp));
			params.addPart("image", new FileBody(image));
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
				
		ActionEngine.json = ActionEngine.jsonParser.makeHttpFileRequest(ActionEngine.donateunique,
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
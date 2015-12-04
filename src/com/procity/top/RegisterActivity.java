package com.procity.top;

import com.procity.util.ActionEngine;
import com.procity.top.R;

import android.app.Activity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	public static EditText username;
	public static EditText password;
	public static EditText email;
	public static EditText confirmpassword;
	public static CheckBox chBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		View loginButton = findViewById(R.id.register_press);
		loginButton.setOnClickListener(this);

		chBox = (CheckBox) findViewById(R.id.rememberChbox);

		username = (EditText) findViewById(R.id.registeruser);
		password = (EditText) findViewById(R.id.registerpass);
		confirmpassword = (EditText) findViewById(R.id.registerconf);
		email = (EditText) findViewById(R.id.registeremail);

	}

	public void onClick(View v) {

		
		if (!ActionEngine.haveNetworkConnection(this)) {

			Toast.makeText(getApplicationContext(),
					"Login Error - check connection", Toast.LENGTH_SHORT).show();
			
			return;
		}
		
		if(password.getText().toString().equals("") || username.getText().toString().equals("")
				|| confirmpassword.getText().toString().equals("") || email.getText().toString().equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Empty fields", Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		

		
		if(!password.getText().toString().equals(confirmpassword.getText().toString())) {
			
			Toast.makeText(getApplicationContext(),
					"Passwords must match", Toast.LENGTH_SHORT).show();
			
			return;
		}
		
		int atindex = email.getText().toString().indexOf("@");
		
		if(atindex <= 0) {
		
			Toast.makeText(getApplicationContext(),
					"Email must be umd.edu extension", Toast.LENGTH_SHORT).show();
			
			return;
			
		}
		
		String rest = email.getText().toString().substring(atindex+1);
		
		if(rest.contains("umd.edu") || rest.contains("terpmail.umd.edu")) {
			
			if (!ActionEngine.haveNetworkConnection(this)) {

				Toast.makeText(getApplicationContext(),
						"Network Error - check connection", Toast.LENGTH_SHORT).show();
				return;
			}
			
			ActionEngine.registerUser(this,v);
			
		} else {
			
			Toast.makeText(getApplicationContext(),
					"Email must be umd.edu extension", Toast.LENGTH_SHORT).show();
			
		}
	
		Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
		startActivity(i);
		finish();
	}

	@Override
	public void onBackPressed() {

		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
		
	}


}
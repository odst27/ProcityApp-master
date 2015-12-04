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

public class LoginActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	public static EditText username;
	public static EditText password;
	public static CheckBox chBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		View loginButton = findViewById(R.id.login_press);
		loginButton.setOnClickListener(this);

		chBox = (CheckBox) findViewById(R.id.rememberChbox);

		username = (EditText) findViewById(R.id.user_field);
		password = (EditText) findViewById(R.id.pass_field);

	}

	public void onClick(View v) {

		if (!ActionEngine.haveNetworkConnection(this)) {

			Toast.makeText(getApplicationContext(),
					"Network Error - check connection", Toast.LENGTH_SHORT).show();
			return;
		}

		ActionEngine.validateUser(this,v);
		
		
	}

	@Override
	public void onBackPressed() {

		Intent i = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
		
	}


}
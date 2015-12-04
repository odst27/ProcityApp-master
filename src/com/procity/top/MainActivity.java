package com.procity.top;

import java.io.File;

import com.procity.util.ActionEngine;
import com.procity.top.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{

    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main_portal);
        
        Button login = (Button) findViewById(R.id.login_main);
        login.setOnClickListener(this);
        
        Button regs = (Button) findViewById(R.id.register_main);
        regs.setOnClickListener(this);
        
	}

	public void onClick(View v) {

		Intent i;
		
		switch(v.getId()){
		
		case R.id.register_main:
			
			
			i = new Intent(getApplicationContext(),
					RegisterActivity.class);
			startActivity(i);
			finish();
			break;
			
		case R.id.login_main:
				
			File f = getBaseContext().getFileStreamPath("savelogin");

			if(f.exists()){
									
				i = new Intent(getApplicationContext(),
						ProcityActivity.class);
			
				startActivity(i);
				ProcityActivity.username = ActionEngine.readCredentials(this);	
				Toast.makeText(getApplicationContext(), "Welcome to Procity " + ProcityActivity.username + "!", Toast.LENGTH_SHORT).show();
				finish();
				
			} else {
				
				i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				finish();
			
			}

		}
		
	}
}

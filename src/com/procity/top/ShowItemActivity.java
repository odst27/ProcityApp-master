package com.procity.top;

import com.androidquery.AQuery;

import com.procity.view.TheCityFragment;
import com.procity.top.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowItemActivity extends Activity {
	
	public static int mode = 0; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		if(mode == 0) {
			
			setContentView(R.layout.showitem);
			
			
			TextView item = (TextView) findViewById(R.id.showitemname);
			TextView cond = (TextView) findViewById(R.id.showcondition);
			TextView desc = (TextView) findViewById(R.id.showdescription);
			TextView user = (TextView) findViewById(R.id.showusername);
			TextView prefloc = (TextView) findViewById(R.id.showprefloc);
			
			Button claimbut = (Button) findViewById(R.id.showbuttonclaim);
			
			item.setText(TheCityFragment.currRecord.itemname);
			cond.setText(TheCityFragment.currRecord.condition);
			desc.setText(TheCityFragment.currRecord.description);
			user.setText(TheCityFragment.currRecord.donatedby);
			
			if(TheCityFragment.currRecord.location != null) {
				
				prefloc.setText(TheCityFragment.currRecord.location);
				
			}
			
			Toast.makeText(getApplicationContext(),
					TheCityFragment.currRecord.location, Toast.LENGTH_SHORT).show();
			
			
			
			claimbut.setText("claim - " + TheCityFragment.currRecord.ebase + " PP");
			
			AQuery aq = new AQuery(this);
			aq.hardwareAccelerated11();
			aq.id(R.id.avatar).image("http://www.myprocity.com/images/" +
					TheCityFragment.currRecord.picpath,true, true, 0, 0, null, AQuery.FADE_IN);

		}
		
	}
	
	@Override
	public void onBackPressed() {
	
		finish();
		super.onBackPressed();
	}
	
}

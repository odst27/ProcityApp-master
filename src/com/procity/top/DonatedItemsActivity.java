package com.procity.top;

import com.procity.top.R;
import com.procity.util.ActionEngine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;

public class DonatedItemsActivity extends Activity implements OnItemClickListener {
	
	public static ListView donatefeedArea;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donateditems_layout);
		
		View view = findViewById(R.layout.donateditems_layout);

		donatefeedArea = (ListView) view.findViewById(R.id.donatedfeedarea);

		donatefeedArea.setLongClickable(true);
		donatefeedArea.setOnItemClickListener(this);
		
		ActionEngine.refreshDonatedItems(this);
	
	}
	
	@Override
	public void onBackPressed() {

		Intent i = new Intent(getApplicationContext(), ProcityActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {
		// TODO Auto-generated method stub
		
	}

}
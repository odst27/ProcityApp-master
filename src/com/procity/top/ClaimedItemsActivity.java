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

public class ClaimedItemsActivity extends Activity implements OnItemClickListener{
	public static ListView claimedfeedArea;

	/** Called when the activity is first created. */

		@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimeditems_layout);
		
		View view = findViewById(R.layout.claimeditems_layout);

		claimedfeedArea = (ListView) view.findViewById(R.id.claimedfeedarea);

		claimedfeedArea.setLongClickable(true);
		claimedfeedArea.setOnItemClickListener(this);
		
		ActionEngine.refreshClaimedItems(this);
	
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
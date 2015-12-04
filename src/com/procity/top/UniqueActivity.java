package com.procity.top;

import java.io.File;

import com.procity.util.ActionEngine;
import com.procity.top.R;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class UniqueActivity extends Activity implements OnItemSelectedListener {
	
	private static final int REQUEST_CODE = 1;
	
	public static Spinner categoryunique;

	private static  TextView unipicpath;
	public static EditText unidescp;
	private static  EditText uniitem;

	public static String unifilename;

	public static String uniquecategorychoice;
	public static String itemchoice;

	public static File unimyFile;
	

	/** Called when the activity is first created. */

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uniquedonate);

		ActionEngine.getFields(this);
		
		categoryunique = (Spinner) findViewById(R.id.uniquecategory);
	
		categoryunique = (Spinner) findViewById(R.id.uniquecategory);
		ArrayAdapter<String> dataAdaptercat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ActionEngine.iRec.getCategories());
		dataAdaptercat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		categoryunique.setAdapter(dataAdaptercat);
		
		categoryunique.setOnItemSelectedListener(this);
	}
	
	public void onDonateUni(View v) {
		
		unipicpath = (TextView) findViewById(R.id.uniquepicpath);

		if(unipicpath.getText().toString().equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Select an image", Toast.LENGTH_SHORT).show();
			return;
			
		}

		unidescp = (EditText) findViewById(R.id.uniquedescrip);
		
		if(unidescp.getText().toString().equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Description empty", Toast.LENGTH_SHORT).show();
			return;
			
		}		

		uniitem = (EditText) findViewById(R.id.uniqueitemname);
		
		itemchoice = uniitem.getText().toString();
		
		if(itemchoice.equals("")) {

			Toast.makeText(getApplicationContext(),
					"Item name empty", Toast.LENGTH_SHORT).show();
			return;
						
		}
		
		if(uniquecategorychoice.equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Fields empty", Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		CheckBox chkbox = (CheckBox) findViewById(R.id.uniquecheckbox);
		
		if(!chkbox.isChecked()) {
			
			Toast.makeText(getApplicationContext(),
					"Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		if (!ActionEngine.haveNetworkConnection(this)) {

			Toast.makeText(getApplicationContext(),
					"Network Error - check connection", Toast.LENGTH_SHORT).show();
			return;
		}
	
		ActionEngine.processUnique(this,v);
	
		Intent i = new Intent(getApplicationContext(), ProcityActivity.class);
		startActivity(i);
		finish();

	}
	
	public void onUniqueBrowse(View v) {
		
		Toast.makeText(getApplicationContext(),
				"Select an image", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, REQUEST_CODE);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			unimyFile = new File(picturePath);
			unifilename = unimyFile.getName();
					    
		    unipicpath = (TextView) findViewById(R.id.uniquepicpath);
		  
		    unipicpath.setText(picturePath);
		   
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	

	@Override
	public void onBackPressed() {

		Intent i = new Intent(getApplicationContext(), ProcityActivity.class);
		startActivity(i);
		finish();
		super.onBackPressed();
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		
		if(parent.getId() == R.id.uniquecategory) {
					
			uniquecategorychoice = parent.getItemAtPosition(pos).toString();
					
		
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}


}
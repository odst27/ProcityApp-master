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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class GenericActivity extends Activity implements OnItemSelectedListener  {
	/** Called when the activity is first created. */

	private static final int REQUEST_CODE = 1;
	private Spinner category;
	private Spinner subcat;
	private Spinner items;
	public static File myFile;
	public static TextView picpath;
	public static TextView descp;
	public static  String filename;
	public static String categorychoice;
	public static String subcategorychoice;
	public static String itemchoice;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.donategeneric);
		
		ActionEngine.getFields(this);
		
		category = (Spinner) findViewById(R.id.genericspinnercat);
		ArrayAdapter<String> dataAdaptercat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, ActionEngine.iRec.getCategories());
		dataAdaptercat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			category.setAdapter(dataAdaptercat);
		
		category.setOnItemSelectedListener(this);
		subcat = (Spinner) findViewById(R.id.genericspinnersub);
		subcat.setOnItemSelectedListener(this);
		items = (Spinner) findViewById(R.id.genericspinneritem);
		items.setOnItemSelectedListener(this);
		
	}
	
	public void onGenBrowse(View v) {
		
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
			myFile = new File(picturePath);
			filename = myFile.getName();
					    
		    picpath = (TextView) findViewById(R.id.genpicpath);
		  
		    picpath.setText(picturePath);
		   
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
	
	public void onDonateGen(View v) {
		
		picpath = (TextView) findViewById(R.id.genpicpath);

		if(picpath.getText().toString().equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Select an image", Toast.LENGTH_SHORT).show();
			return;
			
		}

		descp = (EditText) findViewById(R.id.genericdescription);
		
		if(descp.getText().toString().equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Description empty", Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		if(subcategorychoice.equals("Fill Out Unique Donation") || itemchoice.equals("Fill Out Unique Donation")) {
			
			Toast.makeText(getApplicationContext(),
					"Use unique donation for anything else!", Toast.LENGTH_SHORT).show();
			return;
			
		}
		

		if(categorychoice.equals("") || subcategorychoice.equals("") || itemchoice.equals("")) {
			
			Toast.makeText(getApplicationContext(),
					"Fields empty", Toast.LENGTH_SHORT).show();
			return;
			
		}
		
		CheckBox chkbox = (CheckBox) findViewById(R.id.generictermsandcond);
		
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
	
		ActionEngine.processGeneric(this,v);
	
		Intent i = new Intent(getApplicationContext(), ProcityActivity.class);
		startActivity(i);
		finish();
		
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
		
		if(parent.getId() == R.id.genericspinnercat) {
			
			categorychoice = parent.getItemAtPosition(pos).toString();
			subcat = (Spinner) findViewById(R.id.genericspinnersub);
			
			String [] obj = ActionEngine.iRec.getSubcategories(categorychoice);
			if(obj != null){
				ArrayAdapter<String> dataAdaptersb = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, obj);
				dataAdaptersb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					subcat.setAdapter(dataAdaptersb);
			}
			
		}
		if(parent.getId() == R.id.genericspinnersub) {
		
			subcategorychoice = parent.getItemAtPosition(pos).toString();			
			items = (Spinner) findViewById(R.id.genericspinneritem);
			
			String [] obj = ActionEngine.iRec.getItems(categorychoice, subcategorychoice);
			
			if(obj != null){
				ArrayAdapter<String> dataAdaptersb = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item,obj );
				dataAdaptersb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				items.setAdapter(dataAdaptersb);
			}
		} else if (parent.getId() == R.id.genericspinneritem) {
			
		
			itemchoice = parent.getItemAtPosition(pos).toString();
			
			String emin = ActionEngine.iRec.getEmin(categorychoice, subcategorychoice, itemchoice);
			String emed = ActionEngine.iRec.getEmed(categorychoice, subcategorychoice, itemchoice);
			String emax = ActionEngine.iRec.getEmax(categorychoice, subcategorychoice, itemchoice);
			
			Toast.makeText(getApplicationContext(),
					"Earn ProPoints! Great:" + emax + " Good: " + emed + " Ok: " + emin, Toast.LENGTH_SHORT).show();
		
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}


}
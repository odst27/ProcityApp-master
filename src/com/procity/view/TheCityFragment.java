package com.procity.view;

import com.procity.top.ShowItemActivity;
import com.procity.util.ActionEngine;
import com.procity.util.UserItemAdapter;
import com.procity.util.UserRecord;
import com.procity.top.R;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TheCityFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener, OnClickListener {

	public static ListView feedArea;
	public static String currSwami = "";
	public static String currPost = "";
	private AlertDialog.Builder builder;
	public static UserRecord currRecord;
	final String[] items = { "Claim", "View", "Share" };
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {

			return null;

		}

		View view = inflater.inflate(R.layout.feed_layout, container, false);

		feedArea = (ListView) view.findViewById(R.id.feedarea);

		feedArea.setLongClickable(true);
		feedArea.setOnItemClickListener(this);
		feedArea.setOnItemLongClickListener(this);

		ActionEngine.refreshFeed(this.getActivity());
		
		return view;
	}

	// show jpeg picture
	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

		UserItemAdapter uAdapt = (UserItemAdapter) parent.getAdapter();
		currRecord = uAdapt.getItem(pos);

		//PostViewActivity.currSwami = currRecord.username;
		//PostViewActivity.currPost = currRecord.text;
		
		ShowItemActivity.mode = 0;
		
		Intent i = new Intent(v.getContext(), ShowItemActivity.class);
		startActivity(i);

	}

	public boolean onItemLongClick(AdapterView<?> parent, View v, int pos,
			long id) {

		UserItemAdapter uAdapt = (UserItemAdapter) parent.getAdapter();
		currRecord = uAdapt.getItem(pos);

		//currSwami = currRecord.username;
		//currPost = currRecord.text;

		builder = new AlertDialog.Builder(v.getContext());

		builder.setTitle("Options");
		builder.setItems(items, this);

		builder.create().show();

		return true;
	}

	public void onClick(DialogInterface dialog, int item) {

		String choice = items[item];
			
		if (choice.equals("Favorite")) {
			
			
				
		} else if (choice.equals("Add to mySwamis")) {
			
			/*if(ActionEngine.storemySwami(this.getActivity(),currSwami)){
				
				Toast.makeText(this.getActivity().getBaseContext(), 
						"Added " + currSwami, Toast.LENGTH_SHORT).show();
			} else{
				
				Toast.makeText(this.getActivity().getBaseContext(), 
						currSwami + " is already added", Toast.LENGTH_SHORT).show();
			}*/

		} else if (choice.equals("Share")) {

			AlertDialog ad = new AlertDialog.Builder(this.getActivity())
					.create();
			ad.setCancelable(false); // This blocks the 'BACK' button
			ad.setMessage("Select Method");
			ad.show();

		}
	}

}

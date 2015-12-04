package com.procity.view;

import com.procity.top.R;


import android.content.DialogInterface;

import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class DonateFragment extends Fragment implements OnItemClickListener,
		OnItemLongClickListener, OnClickListener {

	public static String currSwami = "";
	public static String currPost = "";

	//private AlertDialog.Builder builder;


	final String[] items = { "Add to mySwamis", "Remove", "Share" };

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {

			return null;

		}
		
		
		View view = inflater.inflate(R.layout.donate, container,
				false);
	
		return view;
	}
	
	

	// show jpeg picture
	public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

		/*UserItemAdapter uAdapt = (UserItemAdapter) parent.getAdapter();
		currRecord = uAdapt.getItem(pos);

		//PostViewActivity.currSwami = currRecord.username;
		//PostViewActivity.currPost = currRecord.text;
		
		Intent i = new Intent(v.getContext(), PostViewActivity.class);
		startActivity(i);*/
		
	}

	public boolean onItemLongClick(AdapterView<?> parent, View v, int pos,
			long id) {

		/*UserItemAdapter uAdapt = (UserItemAdapter) parent.getAdapter();
		currRecord = uAdapt.getItem(pos);

		//currSwami = currRecord.username;
		//currPost = currRecord.text;

		builder = new AlertDialog.Builder(v.getContext());

		builder.setTitle("Options");
		builder.setItems(items, this);

		builder.create().show();*/

		return true;
	}

	public void onClick(DialogInterface dialog, int item) {

		/*String choice = items[item];

		if (choice.equals("Remove")) {

			ActionEngine.removePost(this.getActivity(), currSwami, currPost);
			ActionEngine.refreshFavorites(this.getActivity());

		} else if (choice.equals("Add to mySwamis")) {

			if (ActionEngine.storemySwami(this.getActivity(), currSwami)) {

				Toast.makeText(this.getActivity().getBaseContext(),
						"Saved " + currSwami, Toast.LENGTH_SHORT).show();
			} else {

				Toast.makeText(this.getActivity().getBaseContext(),
						currSwami + " is already added", Toast.LENGTH_SHORT)
						.show();
			}

		} else if (choice.equals("Share")) {

			AlertDialog ad = new AlertDialog.Builder(this.getActivity())
					.create();
			ad.setCancelable(false); // This blocks the 'BACK' button
			ad.setMessage("Select Method");

			ad.setButton("Clipboard", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

					ActionEngine.copyToClipBoard(DonateFragment.this.getActivity(),"\"" + currPost + "\""
							+ " - " + currSwami);

				}
			});

			ad.setButton2("More", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					ActionEngine.copyToClipBoard(DonateFragment.this.getActivity(),"\"" + currPost + "\""
							+ " - " + currSwami);
					
					Intent tweetIntent = new Intent(Intent.ACTION_SEND);
					tweetIntent.putExtra(Intent.EXTRA_TEXT, "\"" + currPost + "\""
							+ " - " + currSwami);
					tweetIntent.setType("text/plain");

					startActivity(Intent.createChooser(tweetIntent, null));
					
				}
			});
			
			ad.show();

		}*/

	}
}

package com.procity.util;

import java.util.ArrayList;

import com.androidquery.AQuery;
import com.procity.top.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UserItemAdapter extends ArrayAdapter<UserRecord> {
	private ArrayList<UserRecord> users;
	
	public UserItemAdapter(Context context, int textViewResourceId,
			ArrayList<UserRecord> users) {
		super(context, textViewResourceId, users);
		this.users = users;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.listitem, null);
		}

		UserRecord user = users.get(position);
		
		if (user != null) {
			TextView item = (TextView) v.findViewById(R.id.item);
			TextView points = (TextView) v.findViewById(R.id.points);
			
			ImageView pic = (ImageView) v.findViewById(R.id.avatar);
			
			if (item != null) {
				
				item.setText(user.itemname);

			}

			if (points != null) {
				points.setText(user.ebase);
			}
		
			if(pic != null){
				
				AQuery aq = new AQuery(v);
				aq.hardwareAccelerated11();
				aq.id(R.id.avatar).image("http://www.myprocity.com/images/" +
						user.picpath,true, true, 0, 0, null, AQuery.FADE_IN);
				
				
			}
		}
		
		return v;
	}
}

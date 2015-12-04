package com.procity.view;


import com.procity.top.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {

			return null;

		}
		
		View view = inflater.inflate(R.layout.profile, container, false);
		
		return view;
		
	}
	
}

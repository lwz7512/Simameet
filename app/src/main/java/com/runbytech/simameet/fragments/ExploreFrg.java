package com.runbytech.simameet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.actionbarsherlock.app.SherlockFragment;
import com.runbytech.simameet.R;
import com.runbytech.simameet.actions.FragmentTab;

public class ExploreFrg extends SherlockFragment implements FragmentTab{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Get the view from fragment_explore.xml.xml
		View view = inflater.inflate(R.layout.fragment_explore, container, false);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

    public void forceUpdate() {

    }

}

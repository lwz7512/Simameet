package com.runbytech.simameet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.R;
import com.runbytech.simameet.ui.LoginActivity;

public class GroupFrg extends SherlockFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_group, container, false);
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

    @Override
    public void onResume() {
        super.onResume();

        //TODO, check login status

        Context ctx = this.getActivity();

        boolean logged_on = HomeApp.isLoggedOn();
        boolean guest_mode = HomeApp.checkGuestMode();
        if(!logged_on && !guest_mode){
            Intent it = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(it);
        }


    }


}

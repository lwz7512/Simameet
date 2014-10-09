package com.runbytech.simameet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.R;
import com.runbytech.simameet.ui.LoginActivity;

/**
 * Created by liwenzhi on 14-9-22.
 *
 * why not put log on interface here, for the reusable component LoginActivity call.
 * So, any activity can start it!
 *
 */
public class MineFrg  extends SherlockFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab3.xml
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("sima", "on resume...");

        Context ctx = this.getActivity();

        boolean guest_mode = HomeApp.checkGuestMode();
        if(guest_mode){//not logged on, open login form
            Intent it = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(it);
        } else {
            //TODO, fetching logged on user profile...

        }



    }




}

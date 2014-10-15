package com.runbytech.simameet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.runbytech.simameet.R;
import com.runbytech.simameet.actions.FragmentTab;

/**
 * Created by liwenzhi on 14-9-22.
 */
public class CalendarFrg  extends SherlockFragment implements FragmentTab{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the view from fragmenttab3.xml
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    public void forceUpdate() {

    }

}

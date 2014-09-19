package com.runbytech.simameet.utils;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.runbytech.simameet.R;


public class TabUtils {


    /**
     * create tab, and set badge number for it
     *
     * @param context
     * @param tab_layout
     * @param badgeNumber
     * @return
     */
    public static View renderTabView(Context context, int tab_layout, int badgeNumber) {
        FrameLayout view = (FrameLayout) LayoutInflater.from(context).inflate(tab_layout, null);

        updateTabBadge((TextView) view.findViewById(R.id.tab_badge), badgeNumber);

        return view;
    }

    public static void updateTabBadge(ActionBar.Tab tab, int badgeNumber) {
        updateTabBadge((TextView) tab.getCustomView().findViewById(R.id.tab_badge), badgeNumber);
    }

    private static void updateTabBadge(TextView view, int badgeNumber) {
        if (badgeNumber > 0) {
            view.setVisibility(View.VISIBLE);
            view.setText(Integer.toString(badgeNumber));
        }
        else {
            view.setVisibility(View.GONE);
        }
    }



}
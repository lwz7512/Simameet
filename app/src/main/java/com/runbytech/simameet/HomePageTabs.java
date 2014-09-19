/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.runbytech.simameet;


import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.runbytech.simameet.fragments.FragmentTab1;
import com.runbytech.simameet.managers.TabManager;
import com.runbytech.simameet.utils.TabUtils;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments.  It uses a trick (see the code below) to allow
 * the tabs to switch between fragments instead of simple views.
 */
public class HomePageTabs extends SherlockFragmentActivity {

    TabHost mTabHost;
    TabManager mTabManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs);

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.cener_title);

        addTabs(savedInstanceState);

    }



    private void addTabs(Bundle savedInstanceState){
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent){
            @Override
            public void onTabChanged(String tag) {
                changeTitle(tag);
            }
        };


        View explore = this.getLayoutInflater().inflate(R.layout.tab_menu_explore, null);
        mTabManager.addTab(mTabHost.newTabSpec("1").setIndicator(explore),FragmentTab1.class, null);

        View group = this.getLayoutInflater().inflate(R.layout.tab_menu_group, null);
        mTabManager.addTab(mTabHost.newTabSpec("2").setIndicator(group),FragmentTab1.class, null);

        View calendar = this.getLayoutInflater().inflate(R.layout.tab_menu_calendar, null);
        mTabManager.addTab(mTabHost.newTabSpec("3").setIndicator(calendar),FragmentTab1.class, null);

        //add badge
        View message = TabUtils.renderTabView(this,R.layout.tab_menu_message,23);
        mTabManager.addTab(mTabHost.newTabSpec("4").setIndicator(message),FragmentTab1.class, null);

        View me = this.getLayoutInflater().inflate(R.layout.tab_menu_me, null);
        mTabManager.addTab(mTabHost.newTabSpec("5").setIndicator(me),FragmentTab1.class, null);


        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());//save last tab while invisible
    }



    private void showToast(String msg) {
        Toast.makeText(this, "onOptionsItemSelected: " + msg,
                Toast.LENGTH_SHORT).show();
    }

    private void changeTitle(String msg) {
        TextView title = (TextView) findViewById(R.id.custom_bar_title);
        title.setText(msg);
    }


}


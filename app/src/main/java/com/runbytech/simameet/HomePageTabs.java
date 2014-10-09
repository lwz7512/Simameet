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
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.runbytech.simameet.fragments.CalendarFrg;
import com.runbytech.simameet.fragments.ExploreFrg;
import com.runbytech.simameet.fragments.GroupFrg;
import com.runbytech.simameet.fragments.MessageFrg;
import com.runbytech.simameet.fragments.MineFrg;
import com.runbytech.simameet.managers.TabManager;
import com.runbytech.simameet.tasks.ServerConfigTask;
import com.runbytech.simameet.utils.FileLogger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments.  It uses a trick (see the code below) to allow
 * the tabs to switch between fragments instead of simple views.
 */
public class HomePageTabs extends SherlockFragmentActivity {

    private TabHost mTabHost;
    private TabManager mTabManager;
    private HashMap<String, View> tabs;
    private ServerConfigTask getServer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_tabs);

        //hide app title
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //hide app logo
        getSupportActionBar().setDisplayShowHomeEnabled(false);


        //custom header bar
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.cener_title);

        addTabs(savedInstanceState);

        if(HomeApp.checkGuestMode()) getServerConfig();//not logged on
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        HomeApp.setGuestMode(true);
        FileLogger.writeLogFileToSDCard("INFO: app terminated!");
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!HomeApp.checkGuestMode()) lockTabButton(false);//logged on
    }


    private void getServerConfig() {
        //for ui operation to hint user
        getServer = new ServerConfigTask(){
            public void callback(){
                Log.d("mina", "server config execute success!");
                showToast("server conntected!");
                lockTabButton(false);
            }
            public void pullback(){
                Log.d("mina", "server config execute cancel!");
            }
            public void failure(){
                Log.d("mina", "server config execute failure!");
            }
        };
        getServer.execute();
        Log.d("mina", "fetching server token...");

        showToast("connecting server...");

    }

    /**
     * called in onCreate
     *
     * @param savedInstanceState
     */
    private void addTabs(Bundle savedInstanceState){

        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();

        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);

        View explore = this.getLayoutInflater().inflate(R.layout.tab_menu_explore, null);
        mTabManager.addTab(mTabHost.newTabSpec("explore").setIndicator(explore),ExploreFrg.class, null);


        View group = this.getLayoutInflater().inflate(R.layout.tab_menu_group, null);
        mTabManager.addTab(mTabHost.newTabSpec("group").setIndicator(group), GroupFrg.class, null);


        View calendar = this.getLayoutInflater().inflate(R.layout.tab_menu_calendar, null);
        mTabManager.addTab(mTabHost.newTabSpec("calendar").setIndicator(calendar), CalendarFrg.class, null);


        //add badge
        //View message = TabUtils.renderTabView(this,R.layout.tab_menu_message,23);
        View message = this.getLayoutInflater().inflate(R.layout.tab_menu_message, null);
        mTabManager.addTab(mTabHost.newTabSpec("message").setIndicator(message),MessageFrg.class, null);

        View me = this.getLayoutInflater().inflate(R.layout.tab_menu_me, null);
        mTabManager.addTab(mTabHost.newTabSpec("me").setIndicator(me), MineFrg.class, null);


        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

        lockTabButton(true);
    }


    private void lockTabButton(boolean lock) {
        mTabHost.getTabWidget().setEnabled(!lock);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());//save last tab while invisible
    }


    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}


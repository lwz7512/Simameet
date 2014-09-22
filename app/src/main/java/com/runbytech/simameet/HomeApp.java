package com.runbytech.simameet;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liwenzhi on 14-9-22.
 */
public class HomeApp extends Application {

    private static boolean LOGGED_ON = false;

    private static boolean GUEST_MODE = false;

    // 应用上下文
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();
    }

    public static void loggedOn(boolean logon) {
        LOGGED_ON = logon;
    }

    public static boolean isLoggedOn() {
        //used in production evironment
//        SharedPreferences sp = mContext.getSharedPreferences("SP", Activity.MODE_PRIVATE);
//        LOGGED_ON = sp.getBoolean("LOGGED_ON", false);
        
        return LOGGED_ON;
    }

    public static void openGuestMode(){
        GUEST_MODE = true;
    }

    public static boolean checkGuestMode(){
        return GUEST_MODE;
    }


}

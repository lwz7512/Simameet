package com.runbytech.simameet;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.oct.ga.comm.client.StpClient;
import com.runbytech.simameet.config.AppConfig;
import com.runbytech.simameet.tasks.ServerConfigTask;
import com.runbytech.simameet.config.ServiceConfig;
import com.runbytech.simameet.utils.DeviceUtil;

/**
 * Created by liwenzhi on 14-9-22.
 */
public class HomeApp extends Application {

    /**
     * any possible exits? may be removed in future...
     */
    private static boolean LOGGED_ON = false;

    /**
     * initially user not logged on, it's GUEST mode is true,
     * after successful logged on, this value is set to false;
     */
    private static boolean GUEST_MODE = true;

    // 应用上下文
    public static Context mContext;

    /**
     * initialized in ServerConfigTask;
     */
    public static StpClient api;



    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        AppConfig.DEVICE_ID = DeviceUtil.getRawDeviceId(mContext);
        Log.d("mina", "device_id: "+AppConfig.DEVICE_ID);

        AppConfig.OS_VERSION = DeviceUtil.getDeviceOS();
        Log.d("mina", "os: "+AppConfig.OS_VERSION);

    }


    public static void loggedOn(boolean logon) {
        LOGGED_ON = logon;
    }

    public static boolean isLoggedOn() {
//        TODO, used in production environment
//        SharedPreferences sp = mContext.getSharedPreferences("SP", Activity.MODE_PRIVATE);
//        LOGGED_ON = sp.getBoolean("LOGGED_ON", false);
        
        return LOGGED_ON;
    }

    public static void setGuestMode(boolean mode){
        GUEST_MODE = mode;
    }

    public static boolean checkGuestMode(){
        return GUEST_MODE;
    }


    private void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


}

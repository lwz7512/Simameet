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

    private static boolean LOGGED_ON = false;

    private static boolean GUEST_MODE = false;

    // 应用上下文
    public static Context mContext;


    //server returned app ip
    public static String APP_IP;

    //server returned app port
    public static int APP_PORT;

    //server returned token
    public static String TOKEN;

    //logged on user account id
    public static String ACCOUNT_ID;


    //for app later use
    public static StpClient api;


    private ServerConfigTask server;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        AppConfig.DEVICE_ID = DeviceUtil.getRawDeviceId(mContext);
        Log.d("mina", "device_id: "+AppConfig.DEVICE_ID);

        AppConfig.OS_VERSION = DeviceUtil.getDeviceOS();
        Log.d("mina", "os: "+AppConfig.OS_VERSION);

        //use default...
        //AppConfig.VENDOR = DeviceUtil.getDeviceManufacture();
        //Log.d("mina", "vendor: "+AppConfig.VENDOR);

        getServerConfig();
    }



    private void getServerConfig() {
        //for ui operation to hint user
        server = new ServerConfigTask(){
            public void callback(){
                Log.d("mina", "server config execute success!");
                showToast("server conntected!");
            }
            public void pullback(){
                Log.d("mina", "server config execute cancel!");
            }
            public void failure(){
                Log.d("mina", "server config execute failure!");
            }
        };
        server.execute();
        Log.d("mina", "fetching server token...");
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


    private void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();

    }


}

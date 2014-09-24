package com.runbytech.simameet.tasks;

import android.util.Log;

import com.oct.ga.comm.client.StpClient;
import com.oct.ga.comm.cmd.gatekeeper.GK_ACF;
import com.oct.ga.comm.cmd.gatekeeper.GK_ARQ;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.config.AppConfig;
import com.runbytech.simameet.config.ServiceConfig;
import com.runbytech.simameet.vo.GatewayParams;

import org.apache.mina.core.RuntimeIoException;

import java.io.UnsupportedEncodingException;

/**
 * Created by liwenzhi on 14-9-23.
 */
public class ServerConfigTask extends TemplateTask {

    public String test_var;

    //only use once
    private StpClient client;

    private String gatewayIP;
    private int gatewayPort;


    public ServerConfigTask(){
        gatewayIP = ServiceConfig.mina_server_ip;
        gatewayPort = ServiceConfig.mina_server_port;
    }



    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("mina", "to construct arq...");
        GK_ARQ arq = new GK_ARQ();
        Log.d("mian", "ARQ constructed!");

        arq.setDeviceId(AppConfig.DEVICE_ID);
        arq.setVersion(AppConfig.APP_VERSION);//
        arq.setVendorId(AppConfig.VENDOR);//use default..
        arq.setAppId(AppConfig.APP_ID);//use default...

        client = new StpClient();

        try {
            Log.d("mina", "connect to remote gateway...");
            client.start(gatewayIP, gatewayPort);
            Log.d("mina", "gateway connected!");
        } catch (RuntimeIoException e) {
            Log.e("mina", e.getMessage());//uses permission for internet set?
            return false;
        }

        try {
            Log.d("mina","fetching app server config...");
            GK_ACF acf = (GK_ACF) client.send(arq);
            Log.d("mina", "server config obtained!");

            if (acf==null) return false;//not decoded...

            GatewayParams server = new GatewayParams();
            server.setAppServerIp(acf.getServerIp());
            server.setAppServerPort(acf.getPort());
            server.setAppToken(acf.getGateToken());

            Log.d("mina", acf.getGateToken()+"@"+acf.getServerIp()+":"+acf.getPort());

            //save it to global
            ServiceConfig.gateway = server;

            //close connection
            client.close();

            if (server != null){//init api client
                HomeApp.api = new StpClient();
                String ip = server.getAppServerIp();
                int port = server.getAppServerPort();
                try {
                    HomeApp.api.start(ip, port);
                    Log.d("mina", "api client obtained!");
                }catch (Exception e){
                    Log.e("mina", e.getMessage());
                }

                Log.d("mina", "api client started!");
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}

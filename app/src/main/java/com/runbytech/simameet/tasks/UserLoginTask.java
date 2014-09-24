package com.runbytech.simameet.tasks;

/**
 * Created by liwenzhi on 14-9-23.
 */

import android.util.Log;

import com.oct.ga.comm.cmd.auth.LoginReq;
import com.oct.ga.comm.cmd.auth.LoginResp;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.config.AppConfig;
import com.runbytech.simameet.config.ServiceConfig;
import com.runbytech.simameet.utils.DeviceUtil;

import java.io.UnsupportedEncodingException;

/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserLoginTask extends TemplateTask{

    private final String mEmail;
    private final String mPassword;



    public UserLoginTask(String email, String password) {
        mEmail = email;
        mPassword = password;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        LoginReq req = new LoginReq();
        req.setApnsToken("54321");//unknown...
        req.setEmail(mEmail);
        req.setGateToken(ServiceConfig.gateway.getAppToken());
        req.setMyDeviceId(AppConfig.DEVICE_ID);
        req.setOsVersion(DeviceUtil.getDeviceOS());
        req.setPassword(mPassword);
        req.setSequence((int)System.currentTimeMillis()/1000);

        try {
            if(HomeApp.api == null) return false;

            LoginResp resp = (LoginResp) HomeApp.api.send(req);

            String account = resp.getAccountId();
            Log.d("mina", "account logged on: " + account);

            //remember returned value
            HomeApp.ACCOUNT_ID = account;

            if(account==null) return false;//log in failure!


        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }

        // TODO: register the new account here.
        return true;
    }




}

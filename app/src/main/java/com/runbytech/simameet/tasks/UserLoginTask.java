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
import com.runbytech.simameet.utils.NetworkUtil;
import com.runbytech.simameet.vo.AccountParams;

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
        LoginReq req = new LoginReq();
        req.setApnsToken("android_no_apn_token_currently");
        req.setEmail(mEmail);
        req.setGateToken(ServiceConfig.gateway.getAppToken());
        req.setMyDeviceId(AppConfig.DEVICE_ID);
        req.setOsVersion(DeviceUtil.getDeviceOS());
        req.setPassword(mPassword);
        req.setSequence((int)System.currentTimeMillis()/1000);

        try {
            if(HomeApp.api == null) {
                Log.e("sima", "api is null!");
                return false;
            }

            LoginResp resp = (LoginResp) HomeApp.api.send(req);
            if(resp==null) return false;//log in failure!

            String accountId = resp.getAccountId();
            Log.d("sima", "account_id: " + accountId);

            String sessionId = resp.getSessionToken();
            Log.d("sima", "session id: "+sessionId);

            int state = resp.getRespState();
            Log.d("sima", "state value: "+state);

            // invalid account
            if (state != NetworkUtil.RESPONSE_OK) return false;

            AccountParams account = new AccountParams();
            account.setAccountId(accountId);
            account.setSessionId(sessionId);

            //save the newly logged on user
            AppConfig.account = account;

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

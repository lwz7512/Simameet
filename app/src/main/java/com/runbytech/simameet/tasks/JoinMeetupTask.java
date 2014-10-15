package com.runbytech.simameet.tasks;

import android.util.Log;

import com.oct.ga.comm.cmd.club.ActivityJoinReq;
import com.oct.ga.comm.cmd.club.ActivityJoinResp;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.utils.FileLogger;
import com.runbytech.simameet.utils.NetworkUtil;

import java.io.UnsupportedEncodingException;

/**
 * Created by liwenzhi on 14-10-15.
 */
public class JoinMeetupTask extends TemplateTask {

    private String meetupId;

    public JoinMeetupTask(String meetupId){
        this.meetupId = meetupId;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ActivityJoinReq req = new ActivityJoinReq();
        req.setActivityId(this.meetupId);
        req.setSequence((int)System.currentTimeMillis()/1000);

        try {

            if(HomeApp.api == null) {
                Log.e("sima", "api is null!");
                FileLogger.writeLogFileToSDCard("ERROR: api is null!");
                return false;
            }

            ActivityJoinResp resp = (ActivityJoinResp) HomeApp.api.send(req);
            if(resp==null) {
                FileLogger.writeLogFileToSDCard("ERROR: ActivityJoinResp is null!");
                return false;//fetch in failure!
            }
            short result = resp.getRespState();
            if (result != NetworkUtil.RESPONSE_OK){
                return false;
            }

            Log.d("sima", "response state: "+result);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.e("sima", "InterruptedException");
            FileLogger.writeLogFileToSDCard("ERROR: InterruptedException!");
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e("sima", "UnsupportedEncodingException");
            FileLogger.writeLogFileToSDCard("ERROR: UnsupportedEncodingException!");
            return false;
        }

        return true;
    }

}

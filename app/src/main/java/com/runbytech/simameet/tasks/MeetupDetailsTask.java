package com.runbytech.simameet.tasks;

import android.util.Log;

import com.oct.ga.comm.cmd.club.ActivityQueryDetailReq;
import com.oct.ga.comm.cmd.club.ActivityQueryDetailResp;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.utils.FileLogger;
import com.runbytech.simameet.vo.MeetupDetailsVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by liwenzhi on 14-10-14.
 */
public class MeetupDetailsTask extends TemplateTask {

    private String meetupId;
    private MeetupDetailsVO details;


    public MeetupDetailsTask(String meetupId) {
        this.meetupId = meetupId;
    }

    public MeetupDetailsVO getDetails() {
        return details;
    }

    public void setDetails(MeetupDetailsVO details) {
        this.details = details;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ActivityQueryDetailReq req = new ActivityQueryDetailReq();
        req.setActivityId(this.meetupId);
        req.setSequence((int)System.currentTimeMillis()/1000);

        try {

            if(HomeApp.api == null) {
                Log.e("sima", "api is null!");
                FileLogger.writeLogFileToSDCard("ERROR: api is null!");
                return false;
            }

            ActivityQueryDetailResp resp = (ActivityQueryDetailResp) HomeApp.api.send(req);
            if(resp==null) {
                FileLogger.writeLogFileToSDCard("ERROR: ActivityQueryDetailResp is null!");
                return false;//fetch in failure!
            }

            String json = resp.getJson();

            JSONObject jo = new JSONObject(json);

            this.details = MeetupDetailsVO.parseJsonToObj(jo);

            Log.d("sima", json);

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
        }catch (JSONException e) {
            e.printStackTrace();
            Log.e("sima", "JSONException");
            FileLogger.writeLogFileToSDCard("ERROR: JSONException!");
            return false;
        }

        return true;
    }

}

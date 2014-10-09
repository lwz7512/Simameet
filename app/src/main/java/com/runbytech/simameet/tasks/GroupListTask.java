package com.runbytech.simameet.tasks;

import android.util.Log;

import com.oct.ga.comm.cmd.club.ActivityQuerySubscribeReq;
import com.oct.ga.comm.cmd.club.ActivityQuerySubscribeResp;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.utils.FileLogger;
import com.runbytech.simameet.vo.GroupAction;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by liwenzhi on 14-10-8.
 */
public class GroupListTask extends TemplateTask {

    public ArrayList<Object> getActivities() {
        return activities;
    }

    private ArrayList<Object> activities;

    public GroupListTask(){
        activities = new ArrayList<Object>();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        ActivityQuerySubscribeReq req = new ActivityQuerySubscribeReq();
        req.setSequence((int)System.currentTimeMillis()/1000);
        FileLogger.writeLogFileToSDCard("INFO: do in background...");
        try {

            if(HomeApp.api == null) {
                Log.e("sima", "api is null!");
                FileLogger.writeLogFileToSDCard("ERROR: api is null!");
                return false;
            }

            ActivityQuerySubscribeResp resp = (ActivityQuerySubscribeResp)HomeApp.api.send(req);
            if(resp==null) {
                FileLogger.writeLogFileToSDCard("ERROR: ActivityQuerySubscribeResp is null!");
                return false;//fetch in failure!
            }

            String groups = resp.getJson();

            Log.d("sima", groups);

            JSONArray jacts = new JSONArray(groups);

            for(int i=0;i<jacts.length();i++){
                GroupAction ga = GroupAction.parseJsonToObj(jacts.getJSONObject(i));
                activities.add(ga);
            }

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
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("sima", "JSONException");
            FileLogger.writeLogFileToSDCard("ERROR: JSONException!");
            return false;
        }

        FileLogger.writeLogFileToSDCard("INFO: Fetching success!");

        return true;
    }



}

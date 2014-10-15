package com.runbytech.simameet.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.IconButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.R;
import com.runbytech.simameet.fragments.MeetupFrg;
import com.runbytech.simameet.tasks.JoinMeetupTask;
import com.runbytech.simameet.tasks.MeetupDetailsTask;
import com.runbytech.simameet.utils.FileLogger;
import com.runbytech.simameet.utils.TimeUtil;
import com.runbytech.simameet.vo.MeetupDetailsVO;
import com.runbytech.simameet.vo.MeetupVO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MeetupDetails extends SherlockActivity {



    private MeetupDetailsTask task;
    private View mProgressView;

    private TextView joinStatus;
    private IconButton joinBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.meetup_details);

        mProgressView = findViewById(R.id.data_fetching_progress);

        //hide icon
//        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //show home icon to back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String meetupName = getIntent().getStringExtra("meetupName");
        getSupportActionBar().setTitle(meetupName);

        executeDetailTask();

    }


    private void executeDetailTask() {
        String meetupId = getIntent().getStringExtra("meetupId");
        task = new MeetupDetailsTask(meetupId){
            public void callback(){
                //showToast("details fetch success!");
                showProgress(false);
                renderUI(task.getDetails());
                task = null;
            }

            public void failure(){
                showToast("fetch details failure!");
                showProgress(false);
                task = null;
            }

            public void pullback(){
                showProgress(false);
                task = null;
            }
        };
        task.execute();
        showProgress(true);

    }

    /**
     * init text view content with data
     *
     * @param details
     */
    private void renderUI(MeetupDetailsVO details){
        TextView group_name = (TextView)findViewById(R.id.group_name);
        group_name.setText(details.getClubName());

        TextView meetupName = (TextView) findViewById(R.id.meetupName);
        meetupName.setText(details.getName());

        TextView meetupTime = (TextView) findViewById(R.id.meetupTime);
        meetupTime.setText(TimeUtil.secondToDate(details.getStartTime()));

        TextView meetupAddress = (TextView) findViewById(R.id.meetupAddress);
        meetupAddress.setText(details.getLocDesc());

        TextView meetupDescription = (TextView) findViewById(R.id.meetupDescription);
        meetupDescription.setText(details.getDesc());


        TextView group_member_count = (TextView) findViewById(R.id.group_member_count);
        String suffix = getResources().getString(R.string.account_join_suffix);
        group_member_count.setText(String.valueOf(details.getMemberNum())+"  "+suffix);

        //group organizer
        try {
            JSONObject organizer  = details.getMembers().getJSONObject(0);
            TextView group_owner_name = (TextView) findViewById(R.id.group_owner_name);
            group_owner_name.setText(organizer.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //save to global to user later
        joinStatus = (TextView) findViewById(R.id.joinStatus);
        joinBtn = (IconButton) findViewById(R.id.join_meetup_btn);

        int isMember = getIntent().getIntExtra("isMember", 0);
        if(isMember != 0){
            joinStatus.setText(R.string.you_have_joined);
            joinBtn.setVisibility(View.GONE);
        }else{
            joinStatus.setText(R.string.you_are_not_member);
            joinBtn.setVisibility(View.VISIBLE);
        }
        joinBtn.setOnClickListener(new JoinButtonListener(details.getId(),joinBtn));

        //TODO, add organizer avatar ...

    }

    private class  JoinButtonListener implements View.OnClickListener{
        private String meetupId;
        private Button owner;

        public JoinButtonListener(String meetupId, Button btn){
            this.meetupId = meetupId;
            this.owner = btn;
        }
        @Override
        public void onClick( View v) {
            JoinMeetupTask task = new JoinMeetupTask(meetupId){
                public void callback(){
                    showHeaderProgress(false);
                    showToast(R.string.join_meetup_success);

                    joinStatus.setText(R.string.you_have_joined);
                    joinBtn.setVisibility(View.GONE);

                    updateMeetupCache(meetupId);
                }

                public void failure(){
                    showHeaderProgress(false);
                    showToast(R.string.join_meetup_failure);
                }

                public void pullback(){
                    showHeaderProgress(false);
                }
            };
            task.execute();
            showHeaderProgress(true);
            this.owner.setEnabled(false);
            this.owner.setAlpha(0.2f);
        }
    }

    private void updateMeetupCache(String meetupId){
        //refresh cache
        ArrayList<MeetupVO> meetups = (ArrayList)HomeApp.tfdCache.get(MeetupFrg.THIS_KEY);
        for (MeetupVO mv : meetups){
            if(mv.getActId().equals(meetupId)){
                mv.setIsMember(1);//joined!
            }
        }
    }

    public void showHeaderProgress(boolean show){
        setSupportProgressBarIndeterminateVisibility(show);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://back
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);

                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void showProgress(boolean show){
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FileLogger.writeLogFileToSDCard("INFO: meetup ui terminated!");
        if(task!=null){
            task.cancel(true);
            task = null;
        }
    }


    private void showToast(int msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

package com.runbytech.simameet.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.IconButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.runbytech.simameet.R;
import com.runbytech.simameet.tasks.MeetupDetailsTask;
import com.runbytech.simameet.utils.FileLogger;
import com.runbytech.simameet.utils.ScreenUtils;
import com.runbytech.simameet.utils.TimeUtil;
import com.runbytech.simameet.vo.MeetupDetailsVO;

import org.json.JSONException;
import org.json.JSONObject;

public class MeetupDetails extends SherlockActivity {



    private MeetupDetailsTask task;
    private View mProgressView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meetup_details);

        mProgressView = findViewById(R.id.data_fetching_progress);

        //hide icon
//        getSupportActionBar().setDisplayShowHomeEnabled(false);

        //show home icon to back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String meetupName = getIntent().getStringExtra("meetupName");
        getSupportActionBar().setTitle(meetupName);

        executeTask();

    }


    private void executeTask() {
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

        TextView joinStatus = (TextView) findViewById(R.id.joinStatus);
        IconButton joinBtn = (IconButton) findViewById(R.id.join_meetup_btn);
        int isMember = getIntent().getIntExtra("isMember", 0);
        if(isMember != 0){
            joinStatus.setText(R.string.you_have_joined);
            joinBtn.setVisibility(View.GONE);
        }else{
            joinStatus.setText(R.string.you_are_not_member);
            joinBtn.setVisibility(View.VISIBLE);
        }
        //showToast("isMember: "+isMember);

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


}

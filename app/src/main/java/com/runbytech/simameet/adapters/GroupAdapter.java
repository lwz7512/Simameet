package com.runbytech.simameet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.IconButton;
import android.widget.TextView;
import android.widget.Toast;

import com.runbytech.simameet.R;
import com.runbytech.simameet.tasks.JoinMeetupTask;
import com.runbytech.simameet.utils.TimeUtil;
import com.runbytech.simameet.vo.MeetupVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by liwenzhi on 14-10-8.
 */
public class GroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private LayoutInflater inflater;
    private ArrayList<Object> activities;

    private Context ctx;
    private JoinMeetupTask task;


    public GroupAdapter(Context context) {
        this.ctx = context;
        inflater = LayoutInflater.from(context);
        activities = new ArrayList<Object>();
    }

    @Override
    public int getCount() {
        return activities.size();
    }

    @Override
    public Object getItem(int position) {
        return activities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.group_list_item, parent, false);

            holder.ActionTimeText = (TextView) convertView.findViewById(R.id.group_action_time);
            holder.ActionNameText = (TextView) convertView.findViewById(R.id.group_action_name);
            holder.groupNameText = (TextView) convertView.findViewById(R.id.group_title);
            holder.ActionMumberText = (TextView) convertView.findViewById(R.id.group_action_member);
            holder.joinBtn = (IconButton) convertView.findViewById(R.id.join_action_btn);
            holder.joinBtn.setOnClickListener(new JoinButtonListener(position,holder.joinBtn));

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MeetupVO ga = (MeetupVO) activities.get(position);
        holder.ActionNameText.setText(ga.getActName());
        holder.ActionTimeText.setText(TimeUtil.secondToHourMinute(ga.getStartTime()));
        String ren = this.ctx.getString(R.string.account_join_suffix);
        holder.ActionMumberText.setText(String.valueOf(ga.getMemberNum())+ren);
        holder.groupNameText.setText(ga.getClubName());

        if (ga.getIsMember()==1){
            holder.joinBtn.setVisibility(View.GONE);
        }else {
            holder.joinBtn.setVisibility(View.VISIBLE);
        }

        return convertView;
    }


    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.group_list_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.headertext);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        MeetupVO ga = (MeetupVO) activities.get(position);
        int timestamp = ga.getStartTime();//second
        String headerText = TimeUtil.secondToMonDay(timestamp);
        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        MeetupVO ga = (MeetupVO) activities.get(position);
        int timestamp = ga.getStartTime();//second
        long dtl = Long.valueOf(timestamp + "000") ;
        Date dt = new Date(dtl);//millisecond
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(dt);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int milliSecond = calendar.get(Calendar.MILLISECOND);

        long zeroClockMiliSecs = dt.getTime() - hour*3600000 - minute*60000 - second*1000 - milliSecond;

        return zeroClockMiliSecs;
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView ActionNameText;
        TextView ActionMumberText;
        TextView ActionTimeText;
        TextView groupNameText;
        IconButton joinBtn;
    }


    public void refresh(ArrayList<Object> activities){
        this.activities = activities;
        this.notifyDataSetChanged();
    }

    public void clear(){
        this.activities.clear();
        this.notifyDataSetChanged();
    }


    private class JoinButtonListener implements View.OnClickListener{
        private int position ;
        private Button owner;

        JoinButtonListener(int pos, Button btn) {
            position = pos;
            this.owner = btn;
        }

        @Override
        public void onClick( View v) {
            MeetupVO ga = (MeetupVO)getItem(position);
            join(ga.getActId());

            this.owner.setEnabled(false);
            this.owner.setAlpha(0.2f);
        }
    }


    private void join(String meetupId) {
        task = new JoinMeetupTask(meetupId){
            public void callback(){
                showHeaderProgress(false);
                showToast(R.string.join_meetup_success);
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
        //show progress, and disable the button
        showHeaderProgress(true);

    }

    /**
     * callback method by outter class
     */
    public void showHeaderProgress(boolean show){

    }

    private void showToast(String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    private void showToast(int msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

}

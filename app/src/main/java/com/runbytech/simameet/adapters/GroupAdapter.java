package com.runbytech.simameet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runbytech.simameet.R;
import com.runbytech.simameet.vo.GroupAction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by liwenzhi on 14-10-8.
 */
public class GroupAdapter extends BaseAdapter implements StickyListHeadersAdapter {

//    private String[] countries;
    private LayoutInflater inflater;
    private ArrayList<Object> activities;

    public static final DateFormat AGO_FULL_DATE_FORMATTER = new SimpleDateFormat("MM-dd");

    public GroupAdapter(Context context) {
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

            holder.text = (TextView) convertView.findViewById(R.id.group_action_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GroupAction ga = (GroupAction) activities.get(position);
        holder.text.setText(ga.getActName());

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

        GroupAction ga = (GroupAction) activities.get(position);
        int timestamp = ga.getStartTime();//second
        long dtl = Long.valueOf(timestamp + "000") ;
        Date dt = new Date(dtl);//millisecond

        String headerText = AGO_FULL_DATE_FORMATTER.format(dt);

        holder.text.setText(headerText);
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        GroupAction ga = (GroupAction) activities.get(position);
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
        TextView text;
    }


    public void refresh(ArrayList<Object> activities){
        this.activities = activities;
        this.notifyDataSetChanged();
    }


}

package com.runbytech.simameet.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.R;
import com.runbytech.simameet.actions.FragmentTab;
import com.runbytech.simameet.adapters.GroupAdapter;
import com.runbytech.simameet.config.AppConfig;
import com.runbytech.simameet.tasks.MeetupListTask;
import com.runbytech.simameet.ui.MeetupDetails;
import com.runbytech.simameet.ui.LoginActivity;
import com.runbytech.simameet.vo.MeetupVO;

import java.util.ArrayList;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * correspond to Activity menu in bottom
 */
public class MeetupFrg extends SherlockFragment implements AdapterView.OnItemClickListener, FragmentTab{

    public static String THIS_KEY = "MeetupFrg";

    private SherlockFragmentActivity ctx;
    private GroupAdapter adapter;
    private StickyListHeadersListView stickyList;
    private View mProgressView;

    private MeetupListTask task;


    public void forceUpdate() {
        //use caceh data
        if (HomeApp.tfdCache.containsKey(THIS_KEY)){
            adapter.refresh(HomeApp.tfdCache.get(THIS_KEY));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.ctx = (SherlockFragmentActivity)activity;
        Log.d("sima", this.ctx.getLocalClassName());
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_group, container, false);

        mProgressView = view.findViewById(R.id.data_fetching_progress);

        stickyList = (StickyListHeadersListView) view.findViewById(R.id.group_list);
        stickyList.setOnItemClickListener(this);
        adapter = new GroupAdapter(this.getActivity()){
            @Override
            public void showHeaderProgress(boolean show){
                ctx.setSupportProgressBarIndeterminateVisibility(show);
            }
        };
        stickyList.setAdapter(adapter);

        Log.d("sima", "Group fragment create...");

		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true);
	}

    @Override
    public void onResume() {
        super.onResume();

        if(task != null) return;
        if(adapter.getCount() != 0) return;

        //use caceh data
        if (HomeApp.tfdCache.containsKey(THIS_KEY)){
            adapter.refresh(HomeApp.tfdCache.get(THIS_KEY));
            return;
        }

        //check login status
        boolean guest_mode = HomeApp.checkGuestMode();
        if(guest_mode){//not logged on, open login form
            Intent it = new Intent(ctx, LoginActivity.class);
            this.ctx.startActivity(it);
            this.ctx.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
        } else {
            fetch();
        }
 
    }


    private void fetch() {
        if(AppConfig.account == null) return;//not logged on

        //fetching activities...
        task = new MeetupListTask(){
            public void callback(){
                showProgress(false);

                adapter.refresh(task.getActivities());
                HomeApp.tfdCache.put(THIS_KEY, task.getActivities());//cache result

                task = null;
            }

            public void failure(){
                showProgress(false);
                showToast("fetch activities failure!");
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
     * navigate to activity details page...
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent it = new Intent();
        it.setClass(this.ctx, MeetupDetails.class);
        MeetupVO ga = (MeetupVO)adapter.getItem(position);
        it.putExtra("meetupName", ga.getActName());
        it.putExtra("meetupId", ga.getActId());
        it.putExtra("isMember", ga.getIsMember());
        this.ctx.startActivity(it);
        this.ctx.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

    }

    private void showToast(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showProgress(boolean show){
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}

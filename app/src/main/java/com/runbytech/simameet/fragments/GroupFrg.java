package com.runbytech.simameet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.runbytech.simameet.HomeApp;
import com.runbytech.simameet.R;
import com.runbytech.simameet.adapters.GroupAdapter;
import com.runbytech.simameet.tasks.GroupListTask;
import com.runbytech.simameet.ui.LoginActivity;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class GroupFrg extends SherlockFragment {

    private GroupAdapter adapter;
    private View mProgressView;
    private GroupListTask task;

    private static String THIS = "groupFrg";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_group, container, false);

        mProgressView = view.findViewById(R.id.group_fetching_progress);

        StickyListHeadersListView stickyList = (StickyListHeadersListView) view.findViewById(R.id.group_list);
        adapter = new GroupAdapter(this.getActivity());
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
        if (HomeApp.tfdCache.containsKey(THIS)){
            adapter.refresh(HomeApp.tfdCache.get(THIS));
            return;
        }

        Context ctx = this.getActivity();
        //check login status
        boolean guest_mode = HomeApp.checkGuestMode();
        if(guest_mode){//not logged on, open login form
            Intent it = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(it);
        } else {
            //fetching activities...
            task = new GroupListTask(){
                public void callback(){
                    showProgress(false);

                    adapter.refresh(task.getActivities());
                    HomeApp.tfdCache.put(THIS, task.getActivities());

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
            task.execute((Void)null);
            showProgress(true);
        }
 
    }

    private void showToast(String msg) {
        Toast.makeText(this.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showProgress(boolean show){
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}

package com.runbytech.simameet.tasks;

import android.os.AsyncTask;

/**
 * Created by liwenzhi on 14-9-23.
 */
public class TemplateTask extends AsyncTask<Void, Void, Boolean> {



    @Override
    protected Boolean doInBackground(Void... params) {

        return true;
    }


    @Override
    protected void onPostExecute(final Boolean success) {
        if(success) {
            callback();
        } else {
            failure();
        }
    }

    @Override
    protected void onCancelled() {
        pullback();
    }




//    ******** call back area *****************
    /**
     * on success
     *
    ab * @param params
     */
    public void callback() {

    }

    /**
     * on failure
     */
    public void failure() {

    }

    /**
     * on cancel
     */
    public void pullback() {

    }

}

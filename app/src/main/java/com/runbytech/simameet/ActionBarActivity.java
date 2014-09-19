package com.runbytech.simameet;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;


public class ActionBarActivity extends SherlockActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        boolean isLight = false;

//        menu.add("Save")
//                .setIcon(isLight ? R.drawable.ic_compose_inverse : R.drawable.ic_compose)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
//
//        menu.add("Search")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//
//        menu.add("Refresh")
//                .setIcon(isLight ? R.drawable.ic_refresh_inverse : R.drawable.ic_refresh)
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Sherlock); //Used for theme switching in samples
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Toast.makeText(this, "onOptionsItemSelected: "+item.getTitle(),
                Toast.LENGTH_SHORT).show();
        return false;
    }


}

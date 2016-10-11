package com.david.jarealestate;

import android.os.Bundle;

import login.StartFragment;

public class MainActivity extends FirebaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(uk.co.davideandreazzini.jarealestate.R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(uk.co.davideandreazzini.jarealestate.R.id.fragmentView, new StartFragment())
                    .commit();
        }
    }


}

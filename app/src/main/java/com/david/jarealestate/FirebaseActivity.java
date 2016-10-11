package com.david.jarealestate;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by david on 09/10/2016.
 */

public class FirebaseActivity extends AppCompatActivity {
    protected FirebaseAuth mAuth;
    protected FirebaseAuth.AuthStateListener mAuthListener;
    protected boolean loggedIn=false;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        loggedIn = (user!=null);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("INFO", "onAuthStateChanged:signed_in:" + user.getUid());
                    loggedIn=true;
                    goToRoot();

                } else {
                    // User is signed out
                    loggedIn=false;
                    LoginManager.getInstance().logOut();
                    int count = getFragmentManager().getBackStackEntryCount();
                    Log.d("INFO", "Backstack: " + count);
                    Log.d("INFO", "onAuthStateChanged:signed_out");
                    goToMain();
                }
                // ...
            }
        };
    }

    public void goToMain(){
        if(!(this instanceof MainActivity)){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void goToRoot(){
        if(this instanceof MainActivity){
            Intent intent = new Intent(getApplicationContext(), RootActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void onBackPressed() {

            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                Log.i("exit", "should exit");
                finish();
                //additional code
            } else {

                super.onBackPressed();
            }

    }

}

package jarealestate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.google.firebase.analytics.FirebaseAnalytics;

import uk.co.davideandreazzini.jarealestate.R;

/**
 * Created by david on 09/10/2016.
 */

public class SplashActivity extends Activity {
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setContentView(R.layout.activity_splash);
        ImageView iv=(ImageView)findViewById(R.id.image);
        iv.setImageResource(R.drawable.jalogo);
        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, secondsDelayed * 1000);
    }
}

package jarealestate;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import property.BuyPropertyListFragment;
import property.RentPropertyListFragment;
import uk.co.davideandreazzini.jarealestate.R;

public class RootActivity extends FirebaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int oldId,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(uk.co.davideandreazzini.jarealestate.R.layout.activity_root);
        Toolbar toolbar = (Toolbar) findViewById(uk.co.davideandreazzini.jarealestate.R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(uk.co.davideandreazzini.jarealestate.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(uk.co.davideandreazzini.jarealestate.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, uk.co.davideandreazzini.jarealestate.R.string.navigation_drawer_open, uk.co.davideandreazzini.jarealestate.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(uk.co.davideandreazzini.jarealestate.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mAuth = FirebaseAuth.getInstance();
        Log.i("USER", user.toString());
        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(uk.co.davideandreazzini.jarealestate.R.id.fragmentView, new BuyPropertyListFragment())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(uk.co.davideandreazzini.jarealestate.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                Log.i("exit", "should exit");
                System.exit(0);
                //additional code
            } else {

                super.onBackPressed();
            }
        }
    }

    public void goTo(Fragment fragment, Boolean useBackStack){
        FragmentTransaction fragMan = getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.enter_left, R.animator.exit_left,
                        R.animator.exit_right, R.animator.enter_right)
                .replace(uk.co.davideandreazzini.jarealestate.R.id.fragmentView, fragment);
        if(useBackStack) fragMan.addToBackStack(null);
        fragMan.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(uk.co.davideandreazzini.jarealestate.R.menu.root, menu);
        ImageView userImg = (ImageView) findViewById(R.id.userImage);
        Uri photoUrl = user.getPhotoUrl();
        TextView username = (TextView) findViewById(R.id.userName);
        username.setText(user.getDisplayName());
        TextView useremail = (TextView) findViewById(R.id.userEmail);
        useremail.setText(user.getEmail());
        if(photoUrl != null){
            String path = photoUrl.toString();
            try{
                URL newurl = new URL(path);
                Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                userImg.setImageBitmap(mIcon_val);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == uk.co.davideandreazzini.jarealestate.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(uk.co.davideandreazzini.jarealestate.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        new Thread(new Runnable() {
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                    if(id!=oldId){
                        if (id == R.id.nav_buy) {
                            goTo(new BuyPropertyListFragment(), true);
                        } else if (id == R.id.nav_rent) {
                            goTo(new RentPropertyListFragment(), true);
                        } else if (id == R.id.nav_about) {

                        } else if (id == R.id.nav_map) {

                        } else if (id == R.id.nav_logout) {
                            mAuth.signOut();
                        } else if (id == R.id.nav_share) {

                        } else if (id == R.id.nav_send) {

                        }
                    }
                    oldId = id;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return true;
    }

}

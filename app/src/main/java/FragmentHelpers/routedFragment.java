package FragmentHelpers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


/**
 * Created by david on 05/10/2016.
 */

public class routedFragment extends Fragment {
    protected FirebaseAuth mAuth;
    protected DatabaseReference mDatabase;

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    public void goTo(Fragment fragment, Boolean useBackStack){
        FragmentTransaction fragMan = getFragmentManager().beginTransaction()
                .setCustomAnimations(uk.co.davideandreazzini.jarealestate.R.animator.slide_up,
                        uk.co.davideandreazzini.jarealestate.R.animator.slide_down,
                        uk.co.davideandreazzini.jarealestate.R.animator.slide_up,
                        uk.co.davideandreazzini.jarealestate.R.animator.slide_down)
                .replace(uk.co.davideandreazzini.jarealestate.R.id.fragmentView, fragment);
                if(useBackStack) fragMan.addToBackStack(null);
                fragMan.commit();
    }

}

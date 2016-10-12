package login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import helpers.routedFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


/**
 * Created by david on 04/10/2016.
 */

public class LoginFragment extends routedFragment {
    int state;
    Button loginBtn;
    Button backBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(uk.co.davideandreazzini.jarealestate.R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final EditText email = (EditText) getView().findViewById(uk.co.davideandreazzini.jarealestate.R.id.inputEmail);
        final EditText password = (EditText) getView().findViewById(uk.co.davideandreazzini.jarealestate.R.id.inputPassword);

        loginBtn = (Button) getView().findViewById(uk.co.davideandreazzini.jarealestate.R.id.loginBtn);
        if(this.state == 1) loginBtn.setText("Register");
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state==0)
                    loginFn(email.getText().toString(), password.getText().toString());
                else
                    registerFn(email.getText().toString(), password.getText().toString());
            }
        }
        );

        backBtn = (Button) getView().findViewById(uk.co.davideandreazzini.jarealestate.R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { goTo(new StartFragment(), true);}
        });



    }

    public void loginFn(String email, String password){
        if(email.equals("") || password.equals("")) return;
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("INFO", "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("INFO", "signInWithEmail:failed", task.getException());
                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void registerFn(String email, String password){
        if(email.equals("") || password.equals("")) return;
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("INFO", "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(getActivity(), task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }


}

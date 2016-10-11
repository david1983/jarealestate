package property;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import FragmentHelpers.routedFragment;
import property.Property;
import property.PropertyListFragment;
import uk.co.davideandreazzini.jarealestate.R;

import static android.content.ContentValues.TAG;

public class BuyPropertyListFragment extends PropertyListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Buy");
        mDatabase = FirebaseDatabase.getInstance().getReference("BUY");
        Query m = mDatabase.limitToFirst(50);
        loadData.execute(m);
    }

}

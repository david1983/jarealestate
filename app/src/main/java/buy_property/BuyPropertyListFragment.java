package buy_property;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import FragmentHelpers.routedFragment;
import models.Property;
import uk.co.davideandreazzini.jarealestate.R;

import static android.content.ContentValues.TAG;

public class BuyPropertyListFragment extends routedFragment {
    ArrayList<Property> propArray;
    BuyPropertyListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy_property_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        propArray = new ArrayList<Property>();
        adapter = new BuyPropertyListAdapter(getActivity(), propArray);
        final ListView listView = (ListView) getView().findViewById(R.id.buy_list);
        listView.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("BUY");
        Query m = mDatabase.limitToFirst(50);
        loadAsync load = new loadAsync();
        load.execute(m);

    }

    private class loadAsync extends AsyncTask<Query, Void, Void >{

        @Override
        protected Void doInBackground(Query... params) {
            params[0].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Property prop = snapshot.getValue(Property.class);
                        prop.bmp = loadBitmap(prop.mainImageSrc);
                        if(prop.bmp!=null)
                            adapter.add(prop);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });
            return null;
        }

    }

    public Bitmap loadBitmap(String path) {
        try{

            URL newurl = new URL(path);
            return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());

        }catch (IOException e) {
            return  null;
        }
    }


}

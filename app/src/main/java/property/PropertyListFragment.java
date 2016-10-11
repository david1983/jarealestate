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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import FragmentHelpers.routedFragment;
import uk.co.davideandreazzini.jarealestate.R;

import static android.content.ContentValues.TAG;

public class PropertyListFragment extends routedFragment {
    ArrayList<Property> propArray;
    PropertyListAdapter adapter;
    ProgressBar mSpinner;
    TextView title;
    ListView listView;
    protected loadAsync loadData;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        listView.setVisibility(View.INVISIBLE);
        mSpinner.setVisibility(View.VISIBLE);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        propArray = new ArrayList<Property>();
        adapter = new PropertyListAdapter(getActivity(), propArray);


        listView = (ListView) getView().findViewById(R.id.buy_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PropertyDetailFragment detail = new PropertyDetailFragment();
                detail.setArguments(adapter.getItem(position));
                goTo(detail, true);
            }
        });
        title = (TextView) getView().findViewById(R.id.txtTitle);
        mSpinner = (ProgressBar) getView().findViewById(R.id.progressBar3);
        listView.setAdapter(adapter);
        listView.setVisibility(View.INVISIBLE);
        mSpinner.setVisibility(View.VISIBLE);
        loadData = new loadAsync();

    }



    protected class loadAsync extends AsyncTask<Query, Void, Void >{

        @Override
        protected Void doInBackground(Query... params) {

            params[0].addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    listView.setVisibility(View.INVISIBLE);
                    mSpinner.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                        Property prop = snapshot.getValue(Property.class);
                        prop.bmp = loadBitmap(prop.mainImageSrc);
                        if(prop.bmp!=null){
                            title.setText(adapter.getCount() + " properties");
                            adapter.add(prop);
                            listView.setVisibility(View.VISIBLE);
                            mSpinner.setVisibility(View.GONE);
                        }

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

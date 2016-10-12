package property;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import helpers.routedFragment;
import uk.co.davideandreazzini.jarealestate.R;

import static android.content.ContentValues.TAG;

public class PropertyListFragment extends routedFragment {
    ArrayList<Property> propArray;
    PropertyListAdapter adapter;
    ProgressBar mSpinner;
    TextView title;
    ListView listView;
    Button loadMoreBtn;
    String lastKey;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(adapter==null){
            propArray = new ArrayList<Property>();
            adapter = new PropertyListAdapter(getActivity(), propArray);
        }

        loadMoreBtn = (Button) getView().findViewById(R.id.loadMoreBtn);
        listView = (ListView) getView().findViewById(R.id.property_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PropertyDetailFragment detail = new PropertyDetailFragment();
                detail.setArguments(adapter.getItem(position));
                goTo(detail, true);
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int preLast;
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    int i = listView.getScrollY();
                    Log.i("i", "scrolly: " + i);
                }

            @Override
            public void onScroll(AbsListView lw, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                switch(lw.getId())
                {
                    case R.id.property_list:

                        // Make your calculation stuff here. You have all your
                        // needed info from the parameters of this function.

                        // Sample calculation to determine if the last
                        // item is fully visible.
                        final int lastItem = firstVisibleItem + visibleItemCount;


                        if(lastItem == totalItemCount)
                        {
                            loadMoreBtn.setVisibility(View.VISIBLE);
                            if(preLast!=lastItem)
                            {
                                //to avoid multiple calls for last item
                                Log.d("Last", "Last");
                                preLast = lastItem;

                            }
                        }
                }
            }
        });
        title = (TextView) getView().findViewById(R.id.txtTitle);
        mSpinner = (ProgressBar) getView().findViewById(R.id.progressBar3);
        listView.setAdapter(adapter);
        if(adapter.getCount()==0){
            listView.setVisibility(View.INVISIBLE);
            mSpinner.setVisibility(View.VISIBLE);
        }else{
            title.setText(adapter.getCount() + " properties");
            listView.setVisibility(View.VISIBLE);
            mSpinner.setVisibility(View.GONE);
        }


    }

    protected void loadData(Query m){
        m.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    lastKey = snapshot.getKey();
                    Property prop = snapshot.getValue(Property.class);
                    prop.bmp = loadBitmap(prop.mainImageSrc);
                    adapter.add(prop);
                }
                listView.setVisibility(View.VISIBLE);
                mSpinner.setVisibility(View.GONE);
                title.setText(adapter.getCount() + " properties");
                loadMoreBtn.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "Loaded 10 more properties",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
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

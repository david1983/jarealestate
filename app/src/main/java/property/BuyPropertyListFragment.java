package property;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import uk.co.davideandreazzini.jarealestate.R;

public class BuyPropertyListFragment extends PropertyListFragment {
    int last;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Buy");
        if(adapter.getCount()==0)
            loadMore();
        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });

    }

    public void loadMore(){
        mDatabase = FirebaseDatabase.getInstance().getReference("BUY");
        Query m = mDatabase.orderByKey();
        if(lastKey!=null) m = m.startAt(lastKey);
        m = m.limitToFirst(10);
        loadData(m);
    }

}

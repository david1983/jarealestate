package property;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RentPropertyListFragment extends PropertyListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Rent");
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
        mDatabase = FirebaseDatabase.getInstance().getReference("RENT");
        Query m = mDatabase.orderByKey();
        if(lastKey!=null) m = m.startAt(lastKey);
        m = m.limitToFirst(10);
        loadData(m);
    }

}

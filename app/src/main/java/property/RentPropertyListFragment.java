package property;

import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class RentPropertyListFragment extends PropertyListFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("Rent");
        mDatabase = FirebaseDatabase.getInstance().getReference("RENT");
        Query m = mDatabase.limitToFirst(50);
        loadData.execute(m);
    }

}

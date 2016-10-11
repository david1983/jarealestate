package property;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import FragmentHelpers.routedFragment;
import uk.co.davideandreazzini.jarealestate.R;

/**
 * Created by callum on 11/10/16.
 */

public class PropertyDetailFragment extends routedFragment {

    Property p;
    ImageView img;
    TextView title, summary;

    public void setArguments(Property p){
        this.p = p;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property_detail, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        img = (ImageView) getView().findViewById(R.id.propertyImage);
        title = (TextView) getView().findViewById(R.id.txtTitle);
        summary = (TextView) getView().findViewById(R.id.txtSummary);
        if(p.bmp!=null)
            img.setImageBitmap(p.bmp);
        title.setText(p.propertyTypeFullDescription);
        summary.setText(p.summary);

    }
}

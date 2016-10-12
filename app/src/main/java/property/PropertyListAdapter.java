package property;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import uk.co.davideandreazzini.jarealestate.R;

/**
 * Created by david on 10/10/2016.
 */

public class PropertyListAdapter extends ArrayAdapter<Property> {
    private static final String TAG = "INFO" ;

    public PropertyListAdapter(Context context, ArrayList<Property> resource) {
        super(context, 0, resource);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Property prop = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_property_list_item, parent, false);
        }
        // Lookup view for data population
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
        final TextView propTitle = (TextView) convertView.findViewById(R.id.TxtTitle);
        propTitle.setText(prop.propertyTypeFullDescription);
        final TextView propDescription = (TextView) convertView.findViewById(R.id.TxtDescription);
        propDescription.setText(prop.summary);
        if(prop.bmp!=null){
            imageView.setImageBitmap(prop.bmp);
        }else{
            imageView.setImageResource(R.drawable.house);
        }

        // Return the completed view to render on screen
        return convertView;
    }




}

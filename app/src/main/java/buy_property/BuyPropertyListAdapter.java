package buy_property;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

import models.Property;
import uk.co.davideandreazzini.jarealestate.R;

/**
 * Created by david on 10/10/2016.
 */

public class BuyPropertyListAdapter extends ArrayAdapter<Property> {
    private static final String TAG = "INFO" ;

    public BuyPropertyListAdapter(Context context, ArrayList<Property> resource) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_buy_property_list_item, parent, false);
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
            imageView.setImageResource(R.drawable.coins);
        }

        // Return the completed view to render on screen
        return convertView;
    }




}

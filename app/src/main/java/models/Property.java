package models;

import android.graphics.Bitmap;

/**
 * Created by david on 10/10/2016.
 */

public class Property {

    public String displayAddress, summary, propertyTypeFullDescription, mainImageSrc,transactionType,propertySubType  ;
    int bedrooms;
    public Bitmap bmp;

    public Property() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Property(String displayAddress, String summary, String propertyTypeFullDescription, String mainImageSrc, String transactionType, String propertySubType, int bedrooms) {
        this.displayAddress = displayAddress;
        this.summary = summary;
        this.propertyTypeFullDescription = propertyTypeFullDescription;
        this.mainImageSrc = mainImageSrc;
        this.transactionType = transactionType;
        this.propertySubType = propertySubType;
        this.bedrooms = bedrooms;
    }


}

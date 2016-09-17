package com.richstern.foursqdemo.model;

import com.google.gson.annotations.SerializedName;

public class VenueItem {

    @SerializedName(SerializedNames.VENUE)
    private Venue venue;

    private class SerializedNames {
        public static final String VENUE = "venue";
    }
}

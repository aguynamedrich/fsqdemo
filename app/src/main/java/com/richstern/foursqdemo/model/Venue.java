package com.richstern.foursqdemo.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName(SerializedNames.ID)
    private String id;

    @SerializedName(SerializedNames.NAME)
    private String name;

    @SerializedName(SerializedNames.LOCATION)
    private Location location;

    public LatLng getPosition() {
        if (location != null) {
            return new LatLng(location.getLat(), location.getLng());
        }
        return null;
    }

    private class SerializedNames {
        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String LOCATION = "location";
    }
}

package com.richstern.foursqdemo.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName(SerializedNames.LAT)
    private double lat;

    @SerializedName(SerializedNames.LNG)
    private double lng;

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    private class SerializedNames {
        public static final String LAT = "lat";
        public static final String LNG = "lng";
    }
}

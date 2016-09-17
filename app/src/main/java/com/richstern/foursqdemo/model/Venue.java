package com.richstern.foursqdemo.model;

import com.google.gson.annotations.SerializedName;

public class Venue {

    @SerializedName(SerializedNames.ID)
    private int id;

    @SerializedName(SerializedNames.NAME)
    private String name;

    private class SerializedNames {
        public static final String ID = "id";
        public static final String NAME = "name";
    }
}

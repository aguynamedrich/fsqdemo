package com.richstern.foursqdemo.model;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

public class VenuesResponse {

    private LatLngBounds bounds;
    private List<Venue> venues;

    public VenuesResponse(LatLngBounds bounds, List<Venue> venues) {
        this.bounds = bounds;
        this.venues = venues;
    }

    public List<Venue> getVenues() {
        return venues;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }
}

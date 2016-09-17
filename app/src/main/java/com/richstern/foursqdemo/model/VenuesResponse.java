package com.richstern.foursqdemo.model;

import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

public class VenuesResponse {

    private LatLngBounds bounds;
    private List<VenueItem> venues;

    public VenuesResponse(LatLngBounds bounds, List<VenueItem> venues) {
        this.bounds = bounds;
        this.venues = venues;
    }

    public List<VenueItem> getVenues() {
        return venues;
    }

    public LatLngBounds getBounds() {
        return bounds;
    }
}

package com.richstern.foursqdemo.net;

import com.richstern.foursqdemo.model.Venue;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface FourSquareService {

    @GET("venues/explore")
    Observable<List<Venue>> getVenues();

}

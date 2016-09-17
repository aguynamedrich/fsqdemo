package com.richstern.foursqdemo.net;

import com.richstern.foursqdemo.model.VenuesResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FourSquareService {

    @GET("venues/explore")
    Observable<VenuesResponse> getVenues(
        @Query("ll") String latLong,
        @Query("client_id") String clientId,
        @Query("client_secret") String clientSecret,
        @Query("v") String date);

}

package com.richstern.foursqdemo.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.richstern.foursqdemo.R;
import com.richstern.foursqdemo.model.Venue;
import com.richstern.foursqdemo.model.VenueItem;
import com.richstern.foursqdemo.model.serializers.VenuesDeserializer;
import com.richstern.foursqdemo.net.FourSquareService;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends RxAppCompatActivity implements
    OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    private static final int PERM_REQUEST_CODE_LOCATION = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[] {
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final String FOURSQUARE_BASE_URL = "https://api.foursquare.com/v2/";
    private static final String CLIENT_ID = "KB45V00TQB1PCVWOWRWQP3VPYAAB15BEG5VCZVGA3LADGA4B";
    private static final String CLIENT_SECRET = "J3R4VAADPG4N4IATDCA2NVOU1Q0FQ5LQ0ZS3TBNTFM2DVKFT";

    private GoogleMap map;
    private GoogleApiClient googleApiClient;
    private FourSquareService foursquareService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get a reference to the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Init api client for location request
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        }

        initService();
    }

    private void initService() {
        Type venueItemsTypeToken = new TypeToken<List<VenueItem>>() {}.getType();
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(venueItemsTypeToken, new VenuesDeserializer())
            .create();

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory
            .createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(FOURSQUARE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(rxAdapter)
            .build();

        foursquareService = retrofit.create(FourSquareService.class);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocation();
    }

    private void requestLocation() {
        // Request permissions if necessary
        if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, LOCATION_PERMISSIONS, PERM_REQUEST_CODE_LOCATION);
            return;
        }

        // Request location and init map
        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(position, 11);
        map.moveCamera(cameraUpdate);
        map.addMarker(new MarkerOptions().position(position));

        String latLong = String.format("%f,%f", location.getLatitude(), location.getLongitude());
        foursquareService.getVenues(latLong, CLIENT_ID, CLIENT_SECRET)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(venues -> {
                Toast.makeText(this, "Venues: " + venues.size(), Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERM_REQUEST_CODE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            }
        }
    }
}

package com.richstern.foursqdemo.model.serializers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.richstern.foursqdemo.model.Venue;
import com.richstern.foursqdemo.model.VenuesResponse;

import java.lang.reflect.Type;
import java.util.List;

public class VenuesDeserializer implements JsonDeserializer<VenuesResponse> {

    private static final Gson gson = new Gson();

    @Override
    public VenuesResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject outer = json.getAsJsonObject();
        JsonObject response = outer.get("response").getAsJsonObject();
        JsonObject suggestedBounds = response.get("suggestedBounds").getAsJsonObject();
        JsonObject northEast = suggestedBounds.get("ne").getAsJsonObject();
        JsonObject southWest = suggestedBounds.get("sw").getAsJsonObject();

        double neLat = northEast.get("lat").getAsDouble();
        double neLng = northEast.get("lng").getAsDouble();
        double swLat = southWest.get("lat").getAsDouble();
        double swLng = southWest.get("lng").getAsDouble();

        LatLngBounds bounds = new LatLngBounds(
            new LatLng(swLat, swLng),
            new LatLng(neLat, neLng)
        );

        JsonArray groups = response.get("groups").getAsJsonArray();
        JsonObject group1 = groups.get(0).getAsJsonObject();
        JsonElement items = group1.get("items");
        Type venuesTypeToken = new TypeToken<List<Venue>>() {}.getType();
        List<Venue> venues = gson.fromJson(items, venuesTypeToken);

        return new VenuesResponse(bounds, venues);
    }
}

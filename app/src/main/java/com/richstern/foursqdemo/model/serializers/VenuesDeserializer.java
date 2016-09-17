package com.richstern.foursqdemo.model.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.richstern.foursqdemo.model.VenueItem;

import java.lang.reflect.Type;
import java.util.List;

public class VenuesDeserializer implements JsonDeserializer<List<VenueItem>> {

    private static final Gson gson = new Gson();

    @Override
    public List<VenueItem> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type venueItemsTypeToken = new TypeToken<List<VenueItem>>() {}.getType();
        JsonObject outer = json.getAsJsonObject();
        JsonElement items = outer.get("response").getAsJsonObject().get("groups").getAsJsonArray().get(0).getAsJsonObject().get("items");
        return gson.fromJson(items, venueItemsTypeToken);
    }
}

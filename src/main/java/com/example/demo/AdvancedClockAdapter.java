package com.example.demo;

import com.google.gson.*;
import java.lang.reflect.Type;
public class AdvancedClockAdapter implements JsonSerializer<AdvancedClock>, JsonDeserializer<AdvancedClock> {
    @Override
    public JsonElement serialize(AdvancedClock _clock, Type _type, JsonSerializationContext _context){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("hour",_clock.GetHour());
        jsonObject.addProperty("min",_clock.GetMin());
        jsonObject.addProperty("sec",_clock.GetSec());
        jsonObject.addProperty("price",_clock.GetPrice());
        jsonObject.addProperty("brand",_clock.GetBrandName());
        jsonObject.addProperty("id",_clock.GetID());
        return jsonObject;
    }
    @Override
    public AdvancedClock deserialize(JsonElement _json, Type _type, JsonDeserializationContext _context){
        JsonObject jsonObject = _json.getAsJsonObject();
        String brand = jsonObject.get("brand").getAsString();
        int price = jsonObject.get("price").getAsInt();
        int hour = jsonObject.get("hour").getAsInt();
        int min = jsonObject.get("min").getAsInt();
        int sec = jsonObject.get("sec").getAsInt();
        int id = jsonObject.get("id").getAsInt();
        return new AdvancedClock(brand,price,hour,min,sec,id);
    }

}

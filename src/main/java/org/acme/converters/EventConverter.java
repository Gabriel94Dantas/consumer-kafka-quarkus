package org.acme.converters;

import com.google.gson.Gson;
import org.acme.models.Event;

public class EventConverter {

    public static String eventToJson(Event event){
        Gson gson = new Gson();
        String eventString = gson.toJson(event);
        return eventString;
    }

    public static Event jsonToEvent(String jsonString){
        Gson gson = new Gson();
        Event event = gson.fromJson(jsonString, Event.class);
        return event;
    }
}

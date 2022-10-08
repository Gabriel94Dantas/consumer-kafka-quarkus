package org.acme;

import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.services.EventService;

@QuarkusMain
public class Main {

    public static void main(String[] args) {
        EventService eventService = new EventService();
        eventService.getMessages();
    }

}

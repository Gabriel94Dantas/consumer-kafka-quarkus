package org.acme;

import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.acme.services.EventService;

@QuarkusMain
public class ConsumerJob implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        EventService eventService = new EventService();
        eventService.getMessages();
        return 0;
    }
}

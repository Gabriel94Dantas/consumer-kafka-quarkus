package org.acme.models;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Event {
    private String id;
    private String specVersion;
    private String source;
    private String type;
    private String subject;
    private String time;
    private String correlationId;
    private String dataContentType;
    private JsonObject data;
}

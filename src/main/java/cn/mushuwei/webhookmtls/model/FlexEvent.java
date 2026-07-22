package cn.mushuwei.webhookmtls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlexEvent {

    @JsonProperty("event_id")
    private String eventId;

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("payload")
    private Object payload;
}

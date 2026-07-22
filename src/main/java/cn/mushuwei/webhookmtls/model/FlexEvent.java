package cn.mushuwei.webhookmtls.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class FlexEvent {

    private String type;
    private String timestamp;
    private FlexEventData data;
}

package cn.mushuwei.webhookmtls.model;

import lombok.Data;

import java.util.List;

@Data
public class FlexWebhookRequest {

    private String timestamp;
    private List<FlexEvent> data;
}

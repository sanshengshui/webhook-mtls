package cn.mushuwei.webhookmtls.service;

import cn.mushuwei.webhookmtls.model.FlexEvent;
import cn.mushuwei.webhookmtls.model.FlexWebhookRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class EventProcessorService {

    @Async("taskExecutor")
    public void processAsync(FlexWebhookRequest request) {
        try {
            log.info("Processing webhook request...");

            // 遍历处理每个事件
            if (request.getData() != null) {
                for (FlexEvent event : request.getData()) {
                    log.info("Event type: {}, subscriptionId: {}",
                            event.getType(),
                            event.getData() != null ? event.getData().getSubscriptionId() : "N/A");

                    // 根据 event.getType() 做不同业务处理
                    switch (event.getType()) {
                        case "smartflex:subscription:test":
                            handleSubscriptionTest(event);
                            break;
                        default:
                            log.warn("Unknown event type: {}", event.getType());
                    }
                }
            }

            log.info("Webhook request processed successfully.");
        } catch (Exception e) {
            log.error("Failed to process webhook request: {}", e.getMessage(), e);
        }
    }

    private void handleSubscriptionTest(FlexEvent event) {
        // 处理订阅测试事件的具体业务逻辑
        String subscriptionId = event.getData().getSubscriptionId();
        log.info("Processing subscription test for: {}", subscriptionId);
        // TODO: 实际的业务处理
    }
}

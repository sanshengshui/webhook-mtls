package cn.mushuwei.webhookmtls.service;

import cn.mushuwei.webhookmtls.model.FlexEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventProcessorService {

    @Async("taskExecutor")
    public void processAsync(FlexEvent event) {
        try {
            log.info("Processing event: {}, payload: {}", event.getEventId(), event.getPayload());
            // 模拟业务耗时（例如调用外部 API、更新数据库）
            Thread.sleep(2000);
            log.info("Event {} processed successfully.", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to process event {}: {}", event.getEventId(), e.getMessage(), e);
        }
    }
}

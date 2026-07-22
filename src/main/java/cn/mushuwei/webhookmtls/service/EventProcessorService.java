package cn.mushuwei.webhookmtls.service;

import cn.mushuwei.webhookmtls.model.FlexEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class EventProcessorService {

    @Async("taskExecutor")
    public void processAsync(Map<String, Object> params) {
        try {
            log.info("Processing payload: {}", params);
            // 模拟业务耗时（例如调用外部 API、更新数据库）

        } catch (Exception e) {
            log.error("Failed to process event {}", e.getMessage());
        }
    }
}

package cn.mushuwei.webhookmtls.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class IdempotencyService {

    private final Map<String, Long> cache = new ConcurrentHashMap<>();


    @Scheduled(fixedDelay = 60_000)
    public void cleanUp() {
        long now = System.currentTimeMillis();
        int before = cache.size();
        cache.entrySet().removeIf(entry -> now > entry.getValue());
        int after = cache.size();
        if (before != after) {
            log.debug("Cleaned up {} expired idempotency entries.", before - after);
        }
    }
}

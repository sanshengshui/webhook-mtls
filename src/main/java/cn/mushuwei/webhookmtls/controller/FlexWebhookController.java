package cn.mushuwei.webhookmtls.controller;

import cn.mushuwei.webhookmtls.model.FlexEvent;
import cn.mushuwei.webhookmtls.service.EventProcessorService;
import cn.mushuwei.webhookmtls.service.IdempotencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.cert.X509Certificate;
import java.util.Map;

@RestController
@RequestMapping("/webhooks/flex")
@Slf4j
public class FlexWebhookController {

    @Autowired
    private IdempotencyService idempotencyService;

    @Autowired
    private EventProcessorService processorService;

    @PostMapping("/events")
    public ResponseEntity<String> receiveEvent(@RequestBody Map<String, Object> params,
                                               HttpServletRequest request) {
        // 1. 强制校验 mTLS 客户端证书
        X509Certificate[] certs = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if (certs == null || certs.length == 0) {
            log.warn("mTLS client certificate missing - rejecting request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mTLS certificate required");
        }
        log.info("Received event from client: {}", certs[0].getSubjectX500Principal().getName());


        // 4. 异步处理
        processorService.processAsync(params);

        // 5. 立即返回 202 Accepted
        return ResponseEntity.accepted().build();
    }
}

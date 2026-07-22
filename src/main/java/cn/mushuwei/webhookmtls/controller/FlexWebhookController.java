package cn.mushuwei.webhookmtls.controller;

import cn.mushuwei.webhookmtls.model.FlexEvent;
import cn.mushuwei.webhookmtls.model.FlexWebhookRequest;
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
    public ResponseEntity<String> receiveEvent(@RequestBody FlexWebhookRequest request,
                                               HttpServletRequest servletRequest) {
        // 1. 强制校验 mTLS 客户端证书

        // 1. 强制校验 mTLS 客户端证书
        // 【修复】正确的属性名是 "javax.servlet.request.X509Certificate"
        X509Certificate[] certs = (X509Certificate[]) servletRequest
                .getAttribute("javax.servlet.request.X509Certificate");

        if (certs == null || certs.length == 0) {
            log.warn("mTLS client certificate missing - rejecting request");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("mTLS certificate required");
        }

        // 【修复】getSubjectX500Principal() 而不是 getSubjectX509Principal()
        log.info("Received event from client: {}",
                certs[0].getSubjectX500Principal().getName());


        // 4. 异步处理
        processorService.processAsync(request);

        // 5. 立即返回 202 Accepted
        return ResponseEntity.accepted().build();
    }
}

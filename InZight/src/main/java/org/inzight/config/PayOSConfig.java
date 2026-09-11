package org.inzight.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PayOSConfig {

    @Bean
    public PayOS payOS(
            @Value("${spring.payos.client-id}") String clientId,
            @Value("${spring.payos.api-key}") String apiKey,
            @Value("${spring.payos.checksum-key}") String checksumKey) {
        return new PayOS(clientId, apiKey, checksumKey);
    }
}

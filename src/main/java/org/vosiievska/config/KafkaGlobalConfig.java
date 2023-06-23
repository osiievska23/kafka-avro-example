package org.vosiievska.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.kafka-config")
public class KafkaGlobalConfig {

    private String bootstrapServersConfig;
    private String schemaRegistryUrl;
}

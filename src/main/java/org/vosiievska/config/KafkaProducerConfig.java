package org.vosiievska.config;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG;

@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfig<K extends Serializable, V extends SpecificRecordBase> {

  @Bean
  public ProducerFactory<K, V> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigurations());
  }

  @Bean
  public Map<String, Object> producerConfigurations() {
    Map<String, Object> configurations = new HashMap<>();

    configurations.put(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    configurations.put(KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
    configurations.put(KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS, true);
    configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName()); // todo: KafkaAvroSerializer

    return configurations;
  }

  @Bean
  public KafkaTemplate<K, V> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
}

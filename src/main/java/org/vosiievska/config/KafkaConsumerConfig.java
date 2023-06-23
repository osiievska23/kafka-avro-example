package org.vosiievska.config;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.kafka-consumer-config")
@RequiredArgsConstructor
public class KafkaConsumerConfig<K extends Serializable, V extends SpecificRecordBase> {

  private final KafkaGlobalConfig kafkaGlobalConfig;

  private String groupId;

  @Bean
  public ConsumerFactory<K, V> consumerFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfigurations());
  }

  @Bean
  public Map<String, Object> consumerConfigurations() {
    Map<String, Object> configurations = new HashMap<>();

    configurations.put(BOOTSTRAP_SERVERS_CONFIG, kafkaGlobalConfig.getBootstrapServersConfig());
    configurations.put(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, kafkaGlobalConfig.getSchemaRegistryUrl());
    configurations.put(KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS, true);
    configurations.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
    configurations.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    configurations.put(VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());

    return configurations;
  }

  @Bean
  ConcurrentKafkaListenerContainerFactory<K, V> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<K, V> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    return factory;
  }
}

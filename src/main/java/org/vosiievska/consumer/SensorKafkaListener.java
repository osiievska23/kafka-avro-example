package org.vosiievska.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.vosiievska.AvroSensorRequest;

import java.util.List;

@Component
public class SensorKafkaListener implements KafkaConsumer<String, AvroSensorRequest> {

  @Override
  @KafkaListener(
      id = "${app.kafka-consumer-config.group-id}",
      topics = "${app.kafka-config.topic-name}")
  public void listen(@Payload List<AvroSensorRequest> messages,
                     @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                     @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                     @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

    System.out.printf("Received %s messages with keys: %s, partitions: %s and offsets: %s%n",
        messages.size(), keys.toString(), partitions.toString(), offsets.toString());
  }
}

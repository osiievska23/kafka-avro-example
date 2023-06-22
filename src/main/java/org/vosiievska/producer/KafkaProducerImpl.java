package org.vosiievska.producer;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

  private final KafkaTemplate<String, SpecificRecordBase> kmKafkaTemplate;

  public KafkaProducerImpl(KafkaTemplate<String, SpecificRecordBase> kmKafkaTemplate) {
    this.kmKafkaTemplate = kmKafkaTemplate;
  }

  @Override
  public void send(String topicName, String key, SpecificRecordBase message) {
    var completableFuture = kmKafkaTemplate.send(topicName, key, message);
    completableFuture.whenComplete((res, ex) -> {
      if (ex == null) {
        long offset = res.getRecordMetadata().offset();
        System.out.printf("Sent message=[%s] to the topic=[%s] with offset[%s]%n", message, topicName, offset);
      } else {
        System.out.printf("Unable to send message=[%s] with exception message=[%s]%n", message, ex.getMessage());
      }
    });
  }
}

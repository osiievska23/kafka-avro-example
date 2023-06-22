package org.vosiievska.producer;

import org.apache.avro.specific.SpecificRecordBase;

public interface KafkaProducer {

  void send(String topicName, String key, SpecificRecordBase message);

}

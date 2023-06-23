package org.vosiievska.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vosiievska.producer.KafkaProducer;

import static org.vosiievska.util.Utils.buildAvroSensorRequest;

@RestController
public class Controller {
  @Value("${app.kafka-config.topic-name}")
  private String sensorTopicName;

  @Autowired
  private KafkaProducer kafkaProducer;

  @PostMapping("/sensor/{id}")
  public ResponseEntity<String> producerAvroMessage(@PathVariable String id) {
    kafkaProducer.send(sensorTopicName, id, buildAvroSensorRequest(id));
    return ResponseEntity.ok("Sent sensor details data by id " + id);
  }
}

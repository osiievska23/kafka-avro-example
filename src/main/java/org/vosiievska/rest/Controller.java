package org.vosiievska.rest;

import org.apache.kafka.common.metrics.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vosiievska.AvroSensorRequest;
import org.vosiievska.producer.KafkaProducer;

import java.util.Random;

@RestController
public class Controller {

  private static final String TOPIC_NAME = "sensor-topic";

  @Autowired
  private KafkaProducer kafkaProducer;

  @PostMapping("/sensor/{id}")
  public ResponseEntity<String> producerAvroMessage(@PathVariable String id) {
    AvroSensorRequest sensorRequest = new AvroSensorRequest();
    sensorRequest.setId(id);
    sensorRequest.setAcceleration(new Random().nextFloat());
    sensorRequest.setTemperature(new Random().nextFloat());
    sensorRequest.setVelocity(new Random().nextFloat());

    kafkaProducer.send(TOPIC_NAME, id, sensorRequest);
    return ResponseEntity.ok("Sent employee details to consumer");
  }
}

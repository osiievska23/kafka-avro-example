package org.vosiievska.util;

import lombok.NoArgsConstructor;
import org.vosiievska.AvroSensorRequest;

import java.util.Random;

@NoArgsConstructor
public class Utils {

  public static AvroSensorRequest buildAvroSensorRequest(String id) {
    AvroSensorRequest sensorRequest = new AvroSensorRequest();
    sensorRequest.setId(id);
    sensorRequest.setAcceleration(new Random().nextFloat());
    sensorRequest.setTemperature(new Random().nextFloat());
    sensorRequest.setVelocity(new Random().nextFloat());
    return sensorRequest;
  }
}

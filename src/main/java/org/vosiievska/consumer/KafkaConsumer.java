package org.vosiievska.consumer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;
import java.util.List;

public interface KafkaConsumer<K extends Serializable, V extends SpecificRecordBase> {

  void listen(List<V> messages, List<K> keys, List<Integer> partitions, List<Long> offsets);

}

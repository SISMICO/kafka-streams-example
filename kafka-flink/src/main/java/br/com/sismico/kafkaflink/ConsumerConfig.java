package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class ConsumerConfig {
    private String kafkaAddress = "localhost:9092";
    private static String kafkaGroup = "kafka-flink";


    public FlinkKafkaConsumer create(String topic) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        return new FlinkKafkaConsumer<>(
                topic, new SimpleStringSchema(), props);
    }

}
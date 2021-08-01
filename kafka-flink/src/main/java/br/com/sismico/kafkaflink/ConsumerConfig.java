package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ConsumerConfig {

    @Value("${bootstrap.server}")
    private String kafkaAddress;
    private static String kafkaGroup = "kafka-flink";


    public FlinkKafkaConsumer create() {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        return new FlinkKafkaConsumer<>(
                "sismico.client", new SimpleStringSchema(), props);
    }

}

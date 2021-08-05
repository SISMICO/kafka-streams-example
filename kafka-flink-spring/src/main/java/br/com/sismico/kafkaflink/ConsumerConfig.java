package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class ConsumerConfig {

    @Value("${bootstrap.server}")
    private String kafkaAddress;
    private static String kafkaGroup = "kafka-flink";


    public FlinkKafkaConsumer create(String topic) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", kafkaAddress);
        props.setProperty("group.id", kafkaGroup);
        return new FlinkKafkaConsumer<>(
                topic, new SimpleStringSchema(), props);
    }

    public DataStream<String> defineWatermark(StreamExecutionEnvironment env, FlinkKafkaConsumer<String> clientConsumer) {
        return env.addSource(clientConsumer);
//                .assignTimestampsAndWatermarks(new WatermarkStrategy<>() {
//                    @Override
//                    public WatermarkGenerator<String> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
//                        return new AscendingTimestampsWatermarks<>();
//                    }
//
//                    @Override
//                    public TimestampAssigner<String> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
//                        return (element, recordTimestamp) -> System.currentTimeMillis();
//                    }
//                });
    }

    public DataStream<String> getClient(StreamExecutionEnvironment env) {
        FlinkKafkaConsumer<String> clientConsumer = create("sismico.client");
        return defineWatermark(env, clientConsumer);
    }

    public DataStream<String> getAddress(StreamExecutionEnvironment env) {
        FlinkKafkaConsumer<String> addressConsumer = create("sismico.address");
        return defineWatermark(env, addressConsumer);
    }

}

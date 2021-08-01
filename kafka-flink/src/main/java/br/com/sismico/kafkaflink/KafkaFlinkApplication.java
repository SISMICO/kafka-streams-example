package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class KafkaFlinkApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(KafkaFlinkApplication.class);
    private final ConsumerConfig consumerConfig;

    public KafkaFlinkApplication(ConsumerConfig consumer) {
        this.consumerConfig = consumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaFlinkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer<String> consumer = consumerConfig.create();

        DataStreamSource<String> data = env.addSource(consumer);
        SingleOutputStreamOperator<String> result = data.map(String::toUpperCase);
        result.print();
        env.execute();
    }
}

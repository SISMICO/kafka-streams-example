package br.com.sismico.kafkaflink;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaFlinkApplication {
    private static Logger log = LoggerFactory.getLogger(KafkaFlinkApplication.class);
    private final ConsumerConfig consumerConfig = new ConsumerConfig();

    public static void main(String[] args) throws Exception {
        new KafkaFlinkApplication().run();
    }

    private void run() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer<String> clientConsumer = consumerConfig.create("sismico.client");
        FlinkKafkaConsumer<String> addressConsumer = consumerConfig.create("sismico.address");

        DataStream<Client> clientStream = defineWatermark(env, clientConsumer)
                .map((value) -> new Client(value))
                .keyBy(value -> value.getClientId());
        DataStream<Address> addressStream = defineWatermark(env, addressConsumer)
                .map((value) -> {
                    String[] valueList = value.split(":");
                    return new Address(valueList[0], valueList[1]);
                })
                .keyBy(value -> value.getClientId());

        clientStream.addSink(new SinkFunction<Client>() {
            @Override
            public void invoke(Client value, Context context) throws Exception {
                log.info(value.getClientId());
            }
        });
        addressStream.addSink(new SinkFunction<Address>() {
            @Override
            public void invoke(Address value, Context context) throws Exception {
                log.info(value.getAddress());
            }
        });

        DataStream<String> result = clientStream.join(addressStream)
                .where(value -> value.getClientId())
                .equalTo(value -> value.getClientId())
                .window(EventTimeSessionWindows.withGap(Time.seconds(30L)))
                .apply((client, address) -> client.getClientId() + " - " + address.getAddress());

        result.addSink(new SinkFunction<String>() {
            @Override
            public void invoke(String value, Context context) throws Exception {
                log.info(value);
            }
        });
        env.execute();
    }

    private DataStream<String> defineWatermark(StreamExecutionEnvironment env, FlinkKafkaConsumer<String> clientConsumer) {
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
}

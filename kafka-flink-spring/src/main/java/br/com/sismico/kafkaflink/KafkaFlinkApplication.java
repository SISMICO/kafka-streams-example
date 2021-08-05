package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.EventTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaFlinkApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(KafkaFlinkApplication.class);
    //private final NetCatConfig consumerConfig;
    private final ConsumerConfig consumerConfig;

    //public KafkaFlinkApplication(NetCatConfig consumer) {
    public KafkaFlinkApplication(ConsumerConfig consumer) {
        this.consumerConfig = consumer;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaFlinkApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Client> clientStream = consumerConfig.getClient(env)
                .rebalance()
                .map(value -> new Client(value));
        //.keyBy(value -> value.getClientId());
        DataStream<Address> addressStream = consumerConfig.getAddress(env)
                .rebalance()
                .map(value -> {
                    String[] valueList = value.split(":");
                    return new Address(valueList[0], valueList[1]);
                });
        //.keyBy(value -> value.getClientId());

        clientStream.addSink(new SinkFunction<Client>() {
            @Override
            public void invoke(Client value, Context context) throws Exception {
                log.info(value.getClientId().toString());
            }
        });
        addressStream.addSink(new SinkFunction<Address>() {
            @Override
            public void invoke(Address value, Context context) throws Exception {
                log.info(value.getAddress());
            }
        });

//        DataStream<String> result = clientStream.join(addressStream)
//                .where(value -> value.getClientId())
//                .equalTo(value -> value.getClientId())
//                .window(EventTimeSessionWindows.withGap(Time.seconds(10)))
//                .apply((client, address) -> client.getClientId() + " - " + address.getAddress());

        DataStream<String> result = clientStream
                .rebalance()
                .keyBy(value -> value.getClientId())
                .intervalJoin(addressStream.keyBy(value -> value.getClientId()))
                .between(Time.seconds(-10), Time.seconds(60))
                .process(new ProcessJoinFunction<Client, Address, String>() {
                    @Override
                    public void processElement(Client client, Address address, ProcessJoinFunction<Client, Address, String>.Context ctx, Collector<String> out) throws Exception {
                        out.collect(client.getClientId() + " - " + address.getAddress());
                    }
                });

        result.print();

        env.execute("MOISES");
    }
}

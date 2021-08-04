package br.com.sismico.kafkaflink;

import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.stereotype.Service;

@Service
public class NetCatConfig {

    public DataStream<String> getClient(StreamExecutionEnvironment env) {
        return env.socketTextStream("localhost", 5000)
                .assignTimestampsAndWatermarks(new WatermarkStrategy<String>() {
                    @Override
                    public WatermarkGenerator<String> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
                        return new AscendingTimestampsWatermarks<>();
                    }

                    @Override
                    public TimestampAssigner<String> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
                        return new TimestampAssigner<String>() {
                            @Override
                            public long extractTimestamp(String element, long recordTimestamp) {
                                return System.currentTimeMillis();
                            }
                        };
                    }
                });
    }

    public DataStream<String> getAddress(StreamExecutionEnvironment env) {
        return env.socketTextStream("localhost", 5001)
                .assignTimestampsAndWatermarks(new WatermarkStrategy<String>() {
                    @Override
                    public WatermarkGenerator<String> createWatermarkGenerator(WatermarkGeneratorSupplier.Context context) {
                        return new AscendingTimestampsWatermarks<>();
                    }

                    @Override
                    public TimestampAssigner<String> createTimestampAssigner(TimestampAssignerSupplier.Context context) {
                        return new TimestampAssigner<String>() {
                            @Override
                            public long extractTimestamp(String element, long recordTimestamp) {
                                return System.currentTimeMillis();
                            }
                        };
                    }
                });
    }

}

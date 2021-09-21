package com.purbon.kafka;

import org.apache.kafka.tools.ProducerPerformance;

import java.util.Arrays;

public class PerfTest {

    /**
     * usage: producer-performance [-h] --topic TOPIC --num-records NUM-RECORDS
     *                             [--payload-delimiter PAYLOAD-DELIMITER]
     *                             --throughput THROUGHPUT
     *                             [--producer-props PROP-NAME=PROP-VALUE [PROP-NAME=PROP-VALUE ...]]
     *                             [--producer.config CONFIG-FILE]
     *                             [--print-metrics]
     *                             [--transactional-id TRANSACTIONAL-ID]
     *                             [--transaction-duration-ms TRANSACTION-DURATION]
     *                             (--record-size RECORD-SIZE |
     *                             --payload-file PAYLOAD-FILE)
     */
    public static void main(String[] args) throws Exception {

        var toolArgs = Arrays.asList("--topic", "foo",
                "--num-records", "1500",
                "--record-size", "10",
                "--throughput", "-1",
                "--producer-props", "bootstrap.servers=kafka0:9092");
        ProducerPerformance.main(toolArgs.toArray(new String[0]));
    }
}

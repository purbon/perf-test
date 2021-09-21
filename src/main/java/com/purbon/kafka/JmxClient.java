package com.purbon.kafka;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JmxClient {

    public JmxClient() {

    }

    public Map<String, Double> getEnd2EndLatencyStreams(String jmxHost) throws IOException, MalformedObjectNameException {
        String urlString = "service:jmx:rmi:///jndi/rmi://" + jmxHost + "/jmxrmi";
        JMXServiceURL url = new JMXServiceURL(urlString);

        JMXConnector conn = JMXConnectorFactory.connect(url);
        MBeanServerConnection serverCon = conn.getMBeanServerConnection();

        String objectNameString = "kafka.streams:type=stream-processor-node-metrics,*";
        ObjectName objectName = new ObjectName(objectNameString);

        Map<String, Double> latencies = new HashMap<>();

        for(ObjectName name : serverCon.queryNames(objectName, null)) {
            String attrAsString = null;
            try {
                attrAsString = String.valueOf(serverCon.getAttribute(name, "record-e2e-latency-avg"));
            } catch (MBeanException | InstanceNotFoundException | ReflectionException | AttributeNotFoundException e) {
                e.printStackTrace();
            }
            var val = mapFunc().apply(attrAsString);
            latencies.put(name.getCanonicalName(), val);
        }
        return latencies;
    }

    public Function<String, Double> mapFunc() {
        return (s) -> {try {
            return Double.parseDouble(s);
        } catch (Exception ex) {
            return 0d;
        }};
    }

    public static void main(String[] args) throws Exception {
        String jmxHost = "localhost:1099";
        if (args.length != 0) {
            jmxHost = args[0];
        }
        JmxClient client = new JmxClient();
        var latencies = client.getEnd2EndLatencyStreams(jmxHost);
        for(var entry : latencies.entrySet()) {
            System.out.println(entry);
        }
    }
}

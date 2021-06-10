package cn.tiakon.learn.java.core.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Stream;


public class JmxKafkaDemo {

    private MBeanServerConnection conn;
    private  JMXConnector jmxConnector;

    public JmxKafkaDemo(String ipAndPort) {
        String jmxURL = "service:jmx:rmi:///jndi/rmi://" + ipAndPort + "/jmxrmi";
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(jmxURL);
            this.jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);
            this.conn = jmxConnector.getMBeanServerConnection();
            if (conn == null) {
                throw new RuntimeException("Failed to acquire connection...");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }finally {
            try {
                jmxConnector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取 MsgIn 指标的值
    public double getMsgInPerSec() {
        String objectName = "kafka.server:type=BrokerTopicMetrics,name=MessagesInPerSec";
        String objAttr = "OneMinuteRate";
        Object value = getAttribute(objectName, objAttr);
        if (value != null) {
            return (double) (Double) value;
        }
        return 0.0;
    }

    private Object getAttribute(String objName, String objAttr) {
        ObjectName objectName;
        try {
            objectName = new ObjectName(objName);
            return conn.getAttribute(objectName, objAttr);
        } catch (MalformedObjectNameException | MBeanException | AttributeNotFoundException | InstanceNotFoundException | ReflectionException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close(){

    }

    public static void main(String[] args) {
        String[] kafkaJmxAdd = {"120.52.73.147:9393","120.52.73.148:9393","120.52.73.149:9393","120.52.73.150:9393","120.52.73.151:9393","120.52.73.169:9393"};

        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Stream<JmxKafkaDemo> jmxKafkaDemoStream = Arrays.stream(kafkaJmxAdd).map(JmxKafkaDemo::new);
                String totalMessages = jmxKafkaDemoStream.map(JmxKafkaDemo::getMsgInPerSec).reduce(Double::sum).get().toString();
                System.out.println(Float.valueOf(totalMessages));
            }
        };

        timer.scheduleAtFixedRate(timerTask,1000,5*1000);

    }
}


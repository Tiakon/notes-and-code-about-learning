package cn.tiakon.learn.java.core.jmx;

import cn.tiakon.learn.java.core.jmx.entity.Alarm;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

// jmx 实现 https://www.cnblogs.com/longling2344/p/11687051.html
public class JmxDemo02 {

    public static void main(String[] args) {
        JmxDemo02.registerMBean("JmxDemo02", 415);
        JmxDemo02.jmxConnect();
    }

    private static void registerMBean(String module, Integer errorCode) {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName("jmxBean:name=" + module + "-" + errorCode);
            //create mbean and register mbean
            server.registerMBean(new Alarm(), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void jmxConnect() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            LocateRegistry.createRegistry(9999);
            //URL路径的结尾可以随意指定，但如果需要用Jconsole来进行连接，则必须使用jmxrmi
            JMXServiceURL url = new JMXServiceURL
                    ("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnectorServer jcs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            System.out.println("begin rmi start");
            jcs.start();
            System.out.println("rmi start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



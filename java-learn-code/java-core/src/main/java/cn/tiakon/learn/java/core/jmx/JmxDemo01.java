package cn.tiakon.learn.java.core.jmx;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.*;


public class JmxDemo01 {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("please input jmx port!");
            System.exit(0);
        }

        JmxDemo01 main = new JmxDemo01();

        String port = args[0];

        try {
            JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://192.168.0.155:" + port + "/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(address);
            MBeanServerConnection mbs = connector.getMBeanServerConnection();
            String result = main.getClasses(mbs) + ":" + main.getMemory(mbs) + ":" + main.getThread(mbs);
            System.out.println(result);
            connector.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getClasses(MBeanServerConnection mbs) throws IOException {
        ClassLoadingMXBean cbean = ManagementFactory.newPlatformMXBeanProxy(mbs,
                ManagementFactory.CLASS_LOADING_MXBEAN_NAME,
                ClassLoadingMXBean.class);
        return ("ClassLoadingMXBean:\n" +
                "getLoadedClassCount:" + cbean.getLoadedClassCount() + "\n" +
                "getUnloadedClassCount:" + cbean.getUnloadedClassCount() + "\n");

    }

    public String getMemory(MBeanServerConnection mbs) throws IOException {
        MemoryMXBean memory = ManagementFactory.newPlatformMXBeanProxy(mbs,
                ManagementFactory.MEMORY_MXBEAN_NAME,
                MemoryMXBean.class);
        MemoryUsage usage = memory.getHeapMemoryUsage();
        MemoryUsage nonUsage = memory.getNonHeapMemoryUsage();

        return ("MemoryUsage:\n" +
                "getInit:" + usage.getInit() + "\n" +
                "getMax:" + usage.getMax() + "\n" +
                "getUsed:" + usage.getUsed()) +
                (":MemoryNonUsage:" +
                        "getInit:" + nonUsage.getInit() + "\n" +
                        "getMax:" + nonUsage.getMax() + "\n" +
                        "getUsed:" + nonUsage.getUsed() + "\n");
    }

    public String getThread(MBeanServerConnection mbs) throws IOException {
        ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy(mbs,
                ManagementFactory.THREAD_MXBEAN_NAME,
                ThreadMXBean.class);

        return ("ThreadMXBean:\n" +
                "getDaemonThreadCount:" + threadBean.getDaemonThreadCount() + "\n" +
                "getPeakThreadCount:" + threadBean.getPeakThreadCount() + "\n" +
                "getTotalStartedThreadCount:" + threadBean.getTotalStartedThreadCount() + "\n");
    }
}


package cn.tiakon.app.java;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Demo01 {

    public static void main(String[] args) {
        try {
            System.out.println(InetAddress.getLocalHost().toString());

            String name = ManagementFactory.getRuntimeMXBean().getName();
            System.out.println(name);
            // get pid
            String pid = name.split("@")[0];
            String hostname = name.split("@")[1];
            System.out.println("Pid is:" + pid);
            System.out.println("hostname is:" + hostname);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

}

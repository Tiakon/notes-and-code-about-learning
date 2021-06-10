package cn.tiakon.webserver;

import cn.tiakon.webserver.connector.Connector;

public class Bootstrap {
    public static void main(String[] args) {
//        System.setProperty("user.dir","R:\\code\\project-source\\backend-applications\\webserver-tutorial");
        Connector connector = new Connector();
        connector.start();
    }
}

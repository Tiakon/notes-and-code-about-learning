package cn.tiakon.learn.java.core.RuntimeAndProcessBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ProcessBuilderDemo {

    private static final Logger logger = LoggerFactory.getLogger(ProcessBuilderDemo.class);

    public static void main(String[] args) {




    }

    public static int doWaitFor(Process process) {
        int exitValue = -1; // returned to caller when p is finished
        InputStream error = process.getErrorStream();
        InputStream is = process.getInputStream();
        byte[] b = new byte[1024];
        int readbytes = -1;
        try {
            while ((readbytes = error.read(b)) != -1) {
                System.out.println(new String(b, 0, readbytes));
            }
            while ((readbytes = is.read(b)) != -1) {
                System.out.println(new String(b, 0, readbytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                error.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exitValue;
    }

}

package cn.tiakon.learn.java.core.RuntimeAndProcessBuilder;

import java.io.*;

/**
 * 利用 java 程序来编译 java 文件并执行。
 * <p>
 * Java调用外部程序(如Windows下的Exe,或Linux的Shell)
 * https://blog.csdn.net/cuiyaonan2000/article/details/105639624?utm_medium=distribute.pc_relevant.none-task-blog-title-1&spm=1001.2101.3001.4242
 * last time   : 2020/9/28 9:49
 *
 * @author tiankai.me@gmail.com on 2020/9/28 9:49.
 */
public class ExecuteLinuxCommandUtils {

    private final static String WIN_PATHS = "D:\\InstallSoftware\\cygwin64\\bin\\bash";
    private final static String JAVA_HOME = "/cygdrive/d/InstallSoftware/Java/jdk1.8.0_73/bin/java";
    private final static String PATHS = "/bin/sh";

    public static void main(String[] args) {

        System.out.println("开始运行...");

//        String optionSystemVersion = System.getProperty("os.name").toLowerCase();
//        String paths;
//        if (!"linux".equals(optionSystemVersion)) {
//            System.out.println(optionSystemVersion);
//            paths = WIN_PATHS;
//        } else {
//            paths = PATHS;
//        }

        String[] envPaths = {};

        // | : 将上一条命令作为下一条命令的输入。
        String commands1 = "ls -l | sort -k 8 | head -n 3 | awk -F ' ' '{print $9}' ";
        // ; : 连续执行多条命令
        String commands2 = "pwd;ls;cd java-small-app;pwd;ls -l;";

        String commands3 = "bash -version";
        String commands4 = "/cygdrive/d/InstallSoftware/Java/jdk1.8.0_73/bin/java -version";
        String commands5 = "echo $PATH";

//        ExecuteLinuxCommandUtils.execute(commands1, envPaths);
//        ExecuteLinuxCommandUtils.execute(commands2, envPaths);
        ExecuteLinuxCommandUtils.execute(commands3, envPaths);
        System.out.println("-------------------------------");
        ExecuteLinuxCommandUtils.execute(commands4, envPaths);
        System.out.println("-------------------------------");
        ExecuteLinuxCommandUtils.execute(commands5, envPaths);
        System.out.println("-------------------------------");
    }

    public static void execute(String commandStr, String[] envPaths) {
        Runtime rt = Runtime.getRuntime();
        String[] commands = {"D:\\InstallSoftware\\cygwin64\\bin\\bash", "-c", commandStr};
        try {
            //exec方法第一个参数是执行的命令，第二个参数是环境变量，第三个参数是工作目录
            Process pr = rt.exec(commands, envPaths, new File("./"));
            // 获取输出流并转换成缓冲区
            BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));
            //关闭流
            bout.close();
            //SequenceInputStream是一个串联流，能够把两个流结合起来，通过该对象就可以将
            //getInputStream方法和getErrorStream方法获取到的流一起进行查看了，当然也可以单独操作
            SequenceInputStream sis = new SequenceInputStream(pr.getInputStream(), pr.getErrorStream());
            //设置编码格式并转换为输入流
            InputStreamReader inst = new InputStreamReader(sis, "GBK");
            //输入流缓冲区
            BufferedReader br = new BufferedReader(inst);
            String res = null;
            StringBuilder sb = new StringBuilder();
            //循环读取缓冲区中的数据
            while ((res = br.readLine()) != null) {
                sb.append(res).append("\n");
            }
            br.close();
            pr.waitFor();
            pr.destroy();
            //输出获取的数据
            System.out.print(sb);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

}

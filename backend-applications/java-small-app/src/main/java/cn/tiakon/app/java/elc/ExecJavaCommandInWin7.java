package cn.tiakon.app.java.elc;

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
public class ExecJavaCommandInWin7 {

    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("开始运行...");
        String javaHome = "D:\\InstallSoftware\\Java\\jdk1.8.0_73";
        String javaBinPath = javaHome.concat(File.separator).concat("bin");

        String classPath = "CLASSPATH=.;".concat(javaHome).concat(File.separator).concat("lib;")
                .concat(javaHome).concat(File.separator).concat("lib").concat(File.separator).concat("tools.jar");

        String path = "Path=".concat(javaBinPath);

        System.out.println("JavaHome:".concat(javaHome));
        System.out.println("JavaBinPath:".concat(javaBinPath));
        System.out.println("Path:".concat(path));
        System.out.println("ClassPath:".concat(classPath));

        //获得Runtime对象
        Runtime rt = Runtime.getRuntime();

        //执行exec时的环境变量
        String[] envVars = {path, classPath};

        //exec方法第一个参数是执行的命令，第二个参数是环境变量，第三个参数是工作目录
        Process pr = rt.exec("cmd /c javac SqlExample.java && java a", envVars, new File("D://"));

        //获取输出流并转换成缓冲区
        BufferedWriter bout = new BufferedWriter(new OutputStreamWriter(pr.getOutputStream()));

        //输出数据
        bout.write("1 2");

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
    }

}

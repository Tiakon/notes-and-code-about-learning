package cn.tiakon.app.java.elc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * java -cp ./clean-maven-rep-v1.0-SNAPSHOT_build-null.jar cn.tiakon.cleanmavenrep.core.ExecLinuxCommand "XXXXCommand"
 *
 * @author Administrator
 */
public class ExecLinuxCommand {
    public static void main(String[] args) throws IOException {
        for (String param : args) {
            System.out.println(param);
        }
        ExecLinuxCommand linuxCommand = new ExecLinuxCommand();

//        System.out.println("executeShell:");
//        linuxCommand.executeShell(args[0]);

        System.out.println("runShell");
        linuxCommand.runShell("ls -l");
    }

    private void executeShell(String command) {
        Process process = null;
        List<String> processList = new ArrayList<String>();
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : processList) {
            System.out.println(line);
        }
    }

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * 使用 new String[]{"/bin/sh","-c",shStr} 可以使管道命令生效。
     *
     * @param shStr 需要执行的shell
     * @return
     */
    public void runShell(String shStr) {
        List<String> strList = new ArrayList<String>();
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            process.waitFor();
            while ((line = input.readLine()) != null) {
                strList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String line : strList) {
            System.out.println(line);
        }
    }

}

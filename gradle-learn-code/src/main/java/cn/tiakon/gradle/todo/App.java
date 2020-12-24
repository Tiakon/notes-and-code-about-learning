package cn.tiakon.gradle.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author Administrator
 */
public class App {

    private static Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        int i = 0;
        Scanner scanner = new Scanner(System.in);
        while (++i > 0) {
            logger.info("{}. please input todo item...", i);

            String inputStr = new String(scanner.nextLine().getBytes(), Charset.forName("UTF-8"));

            TodoItem item = new TodoItem(inputStr);
            System.out.println(item);
        }
    }

    private void hello() {
        System.out.println("hell");
    }

}

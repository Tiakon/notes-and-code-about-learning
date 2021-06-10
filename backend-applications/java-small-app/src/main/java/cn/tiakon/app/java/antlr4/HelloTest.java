package cn.tiakon.app.java.antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class HelloTest {
    public static void main(String[] args) {
        // 构建输入流、接收数据
        ANTLRInputStream antlrInputStream = new ANTLRInputStream("hello world");
        // 新建一个词法分析器，用于处理输入的stream
        HelloLexer helloLexer = new HelloLexer(antlrInputStream);
        // 新建一个吃法符号的缓冲区，用于存储吃法分析器生成的词法符号
        CommonTokenStream commonTokenStream = new CommonTokenStream(helloLexer);
        // 新建语法分析器
        HelloParser helloParser = new HelloParser(commonTokenStream);
        // 针对规则，开始语法分析
        HelloParser.RContext rContext = helloParser.r();
        // 构建监听器（自己实现的）
        ListenerRewrite listenerRewrite = new ListenerRewrite();
        // 使用监听器初始化对语法分析树的遍历
        ParseTreeWalker.DEFAULT.walk(listenerRewrite,rContext);
    }
}

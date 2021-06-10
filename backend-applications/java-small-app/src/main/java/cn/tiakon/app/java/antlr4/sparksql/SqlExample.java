package cn.tiakon.app.java.antlr4.sparksql;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class SqlExample {
    public static void main(String[] argv) {
        String sqlText = "SELECT DISTINCT C0 C1, C2 FROM T1";
        ANTLRInputStream antlrInputStream = new ANTLRInputStream(sqlText);
        SimpleSqlVisitor visitor = new SimpleSqlVisitor();
        SqlBaseLexer lexer = new SqlBaseLexer(antlrInputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SqlBaseParser parser = new SqlBaseParser(tokenStream);
        SimpleErrorListener errorListener = new SimpleErrorListener();
        parser.addErrorListener(errorListener);
        parser.singleStatement().accept(visitor);
    }
}

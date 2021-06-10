package cn.tiakon.app.java.antlr4;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ListenerRewrite implements HelloListener {
    @Override
    public void enterR(HelloParser.RContext ctx) {
        String aCase = ctx.getChild(0).getText().toLowerCase();
        String bCase = ctx.getChild(1).getText().toLowerCase();
        System.out.println(aCase + " " + bCase);
    }

    @Override
    public void exitR(HelloParser.RContext ctx) {

    }

    @Override
    public void visitTerminal(TerminalNode terminalNode) {

    }

    @Override
    public void visitErrorNode(ErrorNode errorNode) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }
}

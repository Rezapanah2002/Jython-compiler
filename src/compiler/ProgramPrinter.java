package compiler;

import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.List;
import java.util.ArrayList;
import java.lang.System;

public class ProgramPrinter implements japyListener {

    private int indentationLevel = 0;

    @Override
    public void enterProgram(japyParser.ProgramContext ctx) { }

    @Override
    public void exitProgram(japyParser.ProgramContext ctx) { }

    @Override
    public void enterClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        printIndentation();
        String accessModifier = ctx.access_modifier() != null ? ctx.access_modifier().getText() + ", " : "";
        String className = ctx.className.getText();
        String inherits = ctx.classParent != null ? "inherits '" + ctx.classParent.getText() + "'" : "";
        String classDeclaration = "<class '" + className + "'"
                + (!accessModifier.isEmpty() ? accessModifier : "")
                + (!inherits.isEmpty() ? ", " + inherits : "")
                + ">";
        System.out.println(classDeclaration);
        indentationLevel++;
    }

    @Override
    public void exitClassDeclaration(japyParser.ClassDeclarationContext ctx) {
        indentationLevel--;
        printIndentation();
        System.out.println("</class>");
    }

    @Override
    public void enterEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) { }

    @Override
    public void exitEntryClassDeclaration(japyParser.EntryClassDeclarationContext ctx) { }

    @Override
    public void enterFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        printIndentation();
        String accessModifier = ctx.access_modifier() != null ? ctx.access_modifier().getText() + ", " : "";
        String fieldType = ctx.fieldType.getText();
        List<TerminalNode> fieldNames = ctx.ID();

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            output.append(fieldNames.get(i).getText());
            if (i < fieldNames.size() - 1) {
                output.append(", ");
            }
        }
        System.out.println(output + ": (" + accessModifier + "field, " + fieldType + ")");
        indentationLevel++;
    }
    @Override
    public void exitFieldDeclaration(japyParser.FieldDeclarationContext ctx) {
        indentationLevel--;
    }
    @Override
    public void enterAccess_modifier(japyParser.Access_modifierContext ctx) { }

    @Override
    public void exitAccess_modifier(japyParser.Access_modifierContext ctx) { }

    @Override
    public void enterMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        printIndentation();
        String accessModifier = ctx.methodAccessModifier != null ? ctx.methodAccessModifier.getText() + ", " : "";
        String methodName = ctx.methodName.getText();
        List<String> parameters = new ArrayList<>();
        List<TerminalNode> paramIds = ctx.ID();
        List<japyParser.JapyTypeContext> paramTypes = ctx.japyType();

        for (int i = 0; i < paramIds.size(); i++) {
            String paramId = paramIds.get(i).getText();
            String paramType = paramTypes.get(i).getText();
            parameters.add("(" + paramId + ", " + paramType + ")");
        }

        System.out.println("<function '" + methodName + "', " + accessModifier + "parameters: " + parameters + ">");
        indentationLevel++;
    }

    @Override
    public void exitMethodDeclaration(japyParser.MethodDeclarationContext ctx) {
        indentationLevel--;
        printIndentation();
        String returnType = ctx.t.getText();
        String returnvpar = ctx.s.getText();
        System.out.println("</function return ("+ returnvpar.substring(6) + "," + returnType + ")>");
    }

    @Override
    public void enterClosedStatement(japyParser.ClosedStatementContext ctx) { }

    @Override
    public void exitClosedStatement(japyParser.ClosedStatementContext ctx) { }

    @Override
    public void enterClosedConditional(japyParser.ClosedConditionalContext ctx) {
        if (ctx.ifExp != null) {
            printIndentation();
            String condition = ctx.ifExp.getText();
            System.out.println("<if condition: <" + condition + ">>");
            indentationLevel++;
        }
        if (ctx.elifExp != null) {
            printIndentation();
            String condition = ctx.elifExp.getText();
            System.out.println("<elif condition: <" + condition + ">>");
            indentationLevel++;
        }
        if (ctx.elseStmt != null) {
            printIndentation();
            System.out.println("<else>");
            indentationLevel++;
        }
    }

    @Override
    public void exitClosedConditional(japyParser.ClosedConditionalContext ctx) {
        if (ctx.ifExp != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</if>");
        }
        if (ctx.elifExp != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</elif>");
        }
        if (ctx.elseStmt != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</else>");
        }
    }

    @Override
    public void enterOpenConditional(japyParser.OpenConditionalContext ctx) {
        if (ctx.ifExp != null) {
            printIndentation();
            String condition = ctx.ifExp.getText();
            System.out.println("<if condition: <" + condition + ">>");
            indentationLevel++;
        }
        if (ctx.elifExp != null) {
            printIndentation();
            String condition = ctx.elifExp.getText();
            System.out.println("<elif condition: <" + condition + ">>");
            indentationLevel++;
        }
        if (ctx.elseStmt != null) {
            printIndentation();
            System.out.println("<else>");
            indentationLevel++;
        }
    }

    @Override
    public void exitOpenConditional(japyParser.OpenConditionalContext ctx) {
        if (ctx.ifExp != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</if>");
        }
        if (ctx.elifExp != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</elif>");
        }
        if (ctx.elseStmt != null) {
            indentationLevel--;
            printIndentation();
            System.out.println("</else>");
        }
    }

    @Override
    public void enterOpenStatement(japyParser.OpenStatementContext ctx) { }

    @Override
    public void exitOpenStatement(japyParser.OpenStatementContext ctx) { }

    @Override
    public void enterStatement(japyParser.StatementContext ctx) { }

    @Override
    public void exitStatement(japyParser.StatementContext ctx) { }

    @Override
    public void enterStatementVarDef(japyParser.StatementVarDefContext ctx) {
        List<TerminalNode> ids = ctx.ID();
        List<japyParser.ExpressionContext> exprs = ctx.expression();

        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i).getText();
            String expr = exprs.get(i).getText();

            if (expr.startsWith("new")) {
                printIndentation();
                System.out.println(expr + " -> (" + id + ", var)");
                indentationLevel++;
            } else {
                printIndentation();
                System.out.println(expr + " -> " + id);
                indentationLevel++;
            }
        }
    }

    @Override
    public void exitStatementVarDef(japyParser.StatementVarDefContext ctx) {
        indentationLevel--;
    }

    @Override
    public void enterStatementBlock(japyParser.StatementBlockContext ctx) { }

    @Override
    public void exitStatementBlock(japyParser.StatementBlockContext ctx) { }

    @Override
    public void enterStatementContinue(japyParser.StatementContinueContext ctx) { }

    @Override
    public void exitStatementContinue(japyParser.StatementContinueContext ctx) { }

    @Override
    public void enterStatementBreak(japyParser.StatementBreakContext ctx) {
        printIndentation();
        ParserRuleContext parent = ctx.getParent();
        int lineNumber = parent.stop.getLine() + 1;
        System.out.println("goto " + lineNumber + ";");
        indentationLevel++;
    }

    @Override
    public void exitStatementBreak(japyParser.StatementBreakContext ctx) {
        indentationLevel--;
    }

    @Override
    public void enterStatementReturn(japyParser.StatementReturnContext ctx) { }

    @Override
    public void exitStatementReturn(japyParser.StatementReturnContext ctx) { }

    @Override
    public void enterStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        if (ctx.getChild(0).getText().equals("for")) {
            printIndentation();
            String init = ctx.getChild(2).getText();
            String condition = ctx.getChild(4).getText();
            String step = ctx.getChild(6).getText();
            System.out.println("<for init: <" + init + ">, condition: <" + condition + ">, step: <" + step + ">>");
            indentationLevel++;
        } else if (ctx.getChild(0).getText().equals("while")) {
            printIndentation();
            String condition = ctx.getChild(2).getText();
            System.out.println("<while condition: <" + condition + ">>");
            indentationLevel++;
        }
    }

    @Override
    public void exitStatementClosedLoop(japyParser.StatementClosedLoopContext ctx) {
        if (ctx.getChild(0).getText().equals("for")) {
            indentationLevel--;
            printIndentation();
            System.out.println("...");
            System.out.println("</for>");
        } else if (ctx.getChild(0).getText().equals("while")) {
            indentationLevel--;
            printIndentation();
            System.out.println("</while>");
        }
    }

    @Override
    public void enterStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        if (ctx.getChild(0).getText().equals("for")) {
            printIndentation();
            String init = ctx.getChild(2).getText();
            String condition = ctx.getChild(4).getText();
            String step = ctx.getChild(6).getText();
            System.out.println("<for init: <" + init + ">, condition: <" + condition + ">, step: <" + step + ">>");
            indentationLevel++;
        } else if (ctx.getChild(0).getText().equals("while")) {
            printIndentation();
            String condition = ctx.getChild(2).getText();
            System.out.println("<while condition: <" + condition + ">>");
            indentationLevel++;
        }
    }

    @Override
    public void exitStatementOpenLoop(japyParser.StatementOpenLoopContext ctx) {
        if (ctx.getChild(0).getText().equals("for")) {
            indentationLevel--;
            printIndentation();
            System.out.println("...");
            System.out.println("</for>");
        } else if (ctx.getChild(0).getText().equals("while")) {
            indentationLevel--;
            printIndentation();
            System.out.println("</while>");
        }
    }

    @Override
    public void enterStatementWrite(japyParser.StatementWriteContext ctx) { }

    @Override
    public void exitStatementWrite(japyParser.StatementWriteContext ctx) { }

    @Override
    public void enterStatementAssignment(japyParser.StatementAssignmentContext ctx) {
        printIndentation();
        String left = ctx.left.getText();
        String right = ctx.right.getText();
        System.out.println(right + " -> " + left);
        indentationLevel++;
    }

    @Override
    public void exitStatementAssignment(japyParser.StatementAssignmentContext ctx) {
        indentationLevel--;
    }

    @Override
    public void enterStatementInc(japyParser.StatementIncContext ctx) {
        printIndentation();
        String var = ctx.lvalExpr.getText();
        System.out.println("1 + " + var + " -> " + var);
        indentationLevel++;
    }

    @Override
    public void exitStatementInc(japyParser.StatementIncContext ctx) {
        indentationLevel--;
    }

    @Override
    public void enterStatementDec(japyParser.StatementDecContext ctx) {
        printIndentation();
        String var = ctx.lvalExpr.getText();
        System.out.println("1 - " + var + " -> " + var);
        indentationLevel++;
    }

    @Override
    public void exitStatementDec(japyParser.StatementDecContext ctx) {
        indentationLevel--;
    }

    @Override
    public void enterExpression(japyParser.ExpressionContext ctx) { }

    @Override
    public void exitExpression(japyParser.ExpressionContext ctx) { }

    @Override
    public void enterExpressionOr(japyParser.ExpressionOrContext ctx) { }

    @Override
    public void exitExpressionOr(japyParser.ExpressionOrContext ctx) { }

    @Override
    public void enterExpressionOrTemp(japyParser.ExpressionOrTempContext ctx) { }

    @Override
    public void exitExpressionOrTemp(japyParser.ExpressionOrTempContext ctx) { }

    @Override
    public void enterExpressionAnd(japyParser.ExpressionAndContext ctx) { }

    @Override
    public void exitExpressionAnd(japyParser.ExpressionAndContext ctx) { }

    @Override
    public void enterExpressionAndTemp(japyParser.ExpressionAndTempContext ctx) { }

    @Override
    public void exitExpressionAndTemp(japyParser.ExpressionAndTempContext ctx) { }

    @Override
    public void enterExpressionEq(japyParser.ExpressionEqContext ctx) { }

    @Override
    public void exitExpressionEq(japyParser.ExpressionEqContext ctx) { }

    @Override
    public void enterExpressionEqTemp(japyParser.ExpressionEqTempContext ctx) { }

    @Override
    public void exitExpressionEqTemp(japyParser.ExpressionEqTempContext ctx) { }

    @Override
    public void enterExpressionCmp(japyParser.ExpressionCmpContext ctx) { }

    @Override
    public void exitExpressionCmp(japyParser.ExpressionCmpContext ctx) { }

    @Override
    public void enterExpressionCmpTemp(japyParser.ExpressionCmpTempContext ctx) { }

    @Override
    public void exitExpressionCmpTemp(japyParser.ExpressionCmpTempContext ctx) { }

    @Override
    public void enterExpressionAdd(japyParser.ExpressionAddContext ctx) { }

    @Override
    public void exitExpressionAdd(japyParser.ExpressionAddContext ctx) { }

    @Override
    public void enterExpressionAddTemp(japyParser.ExpressionAddTempContext ctx) { }

    @Override
    public void exitExpressionAddTemp(japyParser.ExpressionAddTempContext ctx) { }

    @Override
    public void enterExpressionMultMod(japyParser.ExpressionMultModContext ctx) { }

    @Override
    public void exitExpressionMultMod(japyParser.ExpressionMultModContext ctx) { }

    @Override
    public void enterExpressionMultModTemp(japyParser.ExpressionMultModTempContext ctx) { }

    @Override
    public void exitExpressionMultModTemp(japyParser.ExpressionMultModTempContext ctx) { }

    @Override
    public void enterExpressionUnary(japyParser.ExpressionUnaryContext ctx) { }

    @Override
    public void exitExpressionUnary(japyParser.ExpressionUnaryContext ctx) { }

    @Override
    public void enterExpressionMethods(japyParser.ExpressionMethodsContext ctx) { }

    @Override
    public void exitExpressionMethods(japyParser.ExpressionMethodsContext ctx) { }

    @Override
    public void enterExpressionMethodsTemp(japyParser.ExpressionMethodsTempContext ctx) { }

    @Override
    public void exitExpressionMethodsTemp(japyParser.ExpressionMethodsTempContext ctx) { }

    @Override
    public void enterExpressionOther(japyParser.ExpressionOtherContext ctx) { }

    @Override
    public void exitExpressionOther(japyParser.ExpressionOtherContext ctx) { }

    @Override
    public void enterJapyType(japyParser.JapyTypeContext ctx) { }

    @Override
    public void exitJapyType(japyParser.JapyTypeContext ctx) { }

    @Override
    public void enterSingleType(japyParser.SingleTypeContext ctx) { }

    @Override
    public void exitSingleType(japyParser.SingleTypeContext ctx) { }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) { }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) { }

    @Override
    public void visitTerminal(TerminalNode node) { }

    @Override
    public void visitErrorNode(ErrorNode node) { }

    private void printIndentation() {
        for (int i = 0; i < indentationLevel; i++) {
            System.out.print("    ");
        }
    }
}

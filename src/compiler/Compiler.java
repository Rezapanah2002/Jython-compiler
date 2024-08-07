package compiler;

import gen.japyLexer;
import gen.japyListener;
import gen.japyParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;

public class Compiler {
    public static void main(String[] args) throws IOException {
        String inputFile = "./sample/test3.txt";
        CharStream stream = CharStreams.fromFileName(inputFile);
        japyLexer lexer = new japyLexer(stream);
        TokenStream tokens = new CommonTokenStream(lexer);
        japyParser parser = new japyParser(tokens);
        parser.setBuildParseTree(true);
        ParseTree tree = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();
//        japyListener listener = new ProgramPrinter();
//        japyListener listener = new SymbolTable();
        japyListener listener = new SemanticError();

        walker.walk(listener,tree);
    }
}
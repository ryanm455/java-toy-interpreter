import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) throw new IllegalArgumentException("A file to interpret was not given.");
        String file = Files.readString(Path.of(args[0]));

        Lexer lexer = new Lexer(file);
        Parser parser = new Parser(lexer.getTokens());

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(parser.getParsedStmts().toArray(Stmt[]::new));
    }
}
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public void runFile(Path path) throws IOException {
        String file = Files.readString(path);

        Lexer lexer = new Lexer(file);
        Parser parser = new Parser(lexer.getTokens());

        Interpreter interpreter = new Interpreter();
        interpreter.interpret(parser.getParsedStmts().toArray(Stmt[]::new));
    }

    public void cmdInterpreter() {
        Interpreter interpreter = new Interpreter();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine();

            Lexer lexer = new Lexer(line);
            Parser parser = new Parser(lexer.getTokens());
            interpreter.interpret(parser.getParsedStmts().toArray(Stmt[]::new));
        }
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        if (args.length == 0) {
            main.cmdInterpreter();
        } else {
            main.runFile(Path.of(args[0]));
        }
    }
}
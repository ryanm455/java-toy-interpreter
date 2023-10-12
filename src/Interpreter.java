import java.util.Stack;

public class Interpreter {
    private final Stack<Scope> scopeStack;

    public Interpreter() {
        scopeStack = new Stack<>();
        scopeStack.push(new Scope());
    }

    public void interpret(Stmt... stmts) {
        for (Stmt stmt : stmts) {
            stmt.exec(this.scopeStack);
        }
    }
}

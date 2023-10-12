import java.util.Stack;

public abstract class Stmt {
    public void exec(Stack<Scope> scopeStack) {
        System.out.println("Ran statement: " + this);
    }
}
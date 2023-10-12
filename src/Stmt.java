import java.util.Stack;

public abstract class Stmt {
    public Object exec(Stack<Scope> scopeStack) {
        return null;
    }
}
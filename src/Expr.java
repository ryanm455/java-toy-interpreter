import java.util.Stack;

public abstract class Expr {
    public abstract Object exec(Stack<Scope> scopeStack);
}


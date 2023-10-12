import java.util.Stack;

public class Incr extends Stmt {
    final String var;
    public Incr(String var) {
        this.var = var;
    }

    @Override
    public void exec(Stack<Scope> scopeStack) {
        super.exec(scopeStack);
        Scope currentScope = scopeStack.peek();
        currentScope.assign(var, currentScope.getVar(var) + 1);
    }
}

import java.util.Stack;

public class Incr extends Stmt {
    final String var;

    public Incr(String var) {
        this.var = var;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        Scope currentScope = scopeStack.peek();
        currentScope.assign(var, (int) currentScope.getVar(var) + 1);
        return super.exec(scopeStack);
    }
}

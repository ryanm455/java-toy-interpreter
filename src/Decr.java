import java.util.Stack;

public class Decr extends Stmt {
    final String var;
    public Decr(String var) {
        this.var = var;
    }
    @Override
    public void exec(Stack<Scope> scopeStack) {
        super.exec(scopeStack);
        Scope currentScope = scopeStack.peek();
        currentScope.assign(var, currentScope.getVar(var) - 1);
    }
}

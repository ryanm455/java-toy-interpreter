import java.util.Stack;

public class Clear extends Stmt {
    final String var;
    public Clear(String var) {
        this.var = var;
    }

    @Override
    public void exec(Stack<Scope> scopeStack) {
        super.exec(scopeStack);
        Scope currentScope = scopeStack.peek();
        currentScope.assign(var, 0);
    }
}

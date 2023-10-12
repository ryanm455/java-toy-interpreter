import java.util.Stack;

public class Clear extends Stmt {
    final String var;

    public Clear(String var) {
        this.var = var;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        Scope currentScope = scopeStack.peek();
        currentScope.assign(var, 0);
        return super.exec(scopeStack);
    }
}

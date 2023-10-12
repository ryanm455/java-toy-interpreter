import java.util.Stack;

public class Assign extends Stmt {
    final String varName;
    final Expr value;

    public Assign(String varName, Expr value) {
        this.varName = varName;
        this.value = value;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        scopeStack.peek().assign(varName, value.exec(scopeStack));
        return super.exec(scopeStack);
    }
}

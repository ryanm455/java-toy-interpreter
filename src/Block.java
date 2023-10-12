import java.util.ArrayList;
import java.util.Stack;

public class Block extends Stmt {
    private final ArrayList<Stmt> body;

    public Block(ArrayList<Stmt> body) {
        this.body = body;
    }

    public Object exec(Stack<Scope> scopeStack, Scope currentScope) {
        Object val = null;
        for (Stmt stmt : body) {
            val = stmt.exec(scopeStack);
            if (val != null) break;
        }

        scopeStack.pop();

        return val;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        Scope scope = new Scope(scopeStack.peek());
        scopeStack.push(scope);

        return exec(scopeStack, scope);
    }
}

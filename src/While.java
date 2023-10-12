import java.util.ArrayList;
import java.util.Stack;

public class While extends Stmt  {
    private final Expr cond;
    private final ArrayList<Stmt> body;
    public While(Expr cond, ArrayList<Stmt> body) {
        this.cond = cond;
        this.body = body;
    }

    @Override
    public void exec(Stack<Scope> scopeStack) {
        super.exec(scopeStack);
        Scope currentScope = scopeStack.peek();

        while ((Boolean) cond.exec(scopeStack)) {
            Scope scope = new Scope(currentScope);
            scopeStack.push(scope);

            for (Stmt stmt : body) {
                stmt.exec(scopeStack);
            }

            scopeStack.pop();
        }
    }
}

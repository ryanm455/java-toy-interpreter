import java.util.Stack;

public class ExprStmt extends Stmt {
    final Expr expr;

    public ExprStmt(Expr expr) {
        this.expr = expr;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        expr.exec(scopeStack);
        return super.exec(scopeStack);
    }
}

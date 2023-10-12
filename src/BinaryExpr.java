import java.util.Stack;

public class BinaryExpr extends Expr {
    public final Expr LHS;
    public final Op op;
    public final Expr RHS;

    public BinaryExpr(Expr LHS, Op op, Expr RHS) {
        this.LHS = LHS;
        this.op = op;
        this.RHS = RHS;
    }

    @Override
    public Object exec(Stack<Scope> scope) {
        return LHS.exec(scope) != RHS.exec(scope);
    }
}

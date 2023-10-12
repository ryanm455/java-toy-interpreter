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

    public Object exec(Stack<Scope> scopeStack) {
        Object lhs = LHS.exec(scopeStack);
        Object rhs = RHS.exec(scopeStack);

        return switch (op) {
            case NOT_EQ -> lhs != rhs;
            case EQ -> lhs == rhs;
            case PLUS -> (Integer) lhs + (Integer) rhs;
            case MINUS -> (Integer) lhs - (Integer) rhs;
            case MULT -> (Integer) lhs * (Integer) rhs;
            case DIV -> (Integer) lhs / (Integer) rhs;
            case MOD -> (Integer) lhs % (Integer) rhs;
        };
    }
}

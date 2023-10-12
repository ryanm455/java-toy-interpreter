import java.util.ArrayList;
import java.util.Stack;

public class While extends Block {
    private final Expr cond;

    public While(Expr cond, ArrayList<Stmt> body) {
        super(body);
        this.cond = cond;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        super.exec(scopeStack);

        while ((Boolean) cond.exec(scopeStack)) {
            Object val = super.exec(scopeStack);
            if (val != null) return val;
        }

        return null;
    }
}

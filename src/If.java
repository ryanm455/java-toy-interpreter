import java.util.ArrayList;
import java.util.Stack;

public class If extends Block {
    final Expr cond;
    private Block chained;

    public If(Expr cond, ArrayList<Stmt> body) {
        super(body);
        this.cond = cond;
    }

    public void chain(Block block) {
        chained = block;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        if ((boolean) this.cond.exec(scopeStack)) {
            return super.exec(scopeStack);
        } else if (chained != null) {
            return chained.exec(scopeStack);
        }
        return null;
    }
}

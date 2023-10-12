import java.util.Stack;

public class Return extends Stmt {

    final Expr retVal;

    public Return(Expr retVal) {
        this.retVal = retVal;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        return retVal.exec(scopeStack);
    }
}

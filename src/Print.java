import java.util.Stack;

public class Print extends Stmt {
    private final Expr val;

    public Print(Expr val) {
        this.val = val;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        System.out.println(val.exec(scopeStack));
        return super.exec(scopeStack);
    }
}

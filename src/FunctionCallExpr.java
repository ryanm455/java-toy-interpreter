import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class FunctionCallExpr extends Expr {
    final String fnName;
    final ArrayList<Expr> params;

    public FunctionCallExpr(String fnName, ArrayList<Expr> params) {
        this.fnName = fnName;
        this.params = params;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        ArrayList<Object> params = this.params.stream().map(e -> e.exec(scopeStack)).collect(Collectors.toCollection(ArrayList::new));

        FunctionInst function = scopeStack.peek().getFunction(fnName);
        return function.exec(scopeStack, params);
    }
}

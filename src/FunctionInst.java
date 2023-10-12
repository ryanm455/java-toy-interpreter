import java.util.ArrayList;
import java.util.Stack;

public class FunctionInst extends Block {
    final ArrayList<String> params;

    public FunctionInst(ArrayList<String> params, ArrayList<Stmt> body) {
        super(body);
        this.params = params;
    }

    public Object exec(Stack<Scope> scopeStack, ArrayList<Object> params) {
        if (params.size() != this.params.size()) {
            throw new RuntimeError(String.format("Incorrect function parameters: Given %s, expected %s.", params.size(), this.params.size()));
        }

        Scope scope = new Scope(scopeStack.peek());
        scopeStack.push(scope);

        for (int i = 0; i < params.size(); i++) {
            scope.assign(this.params.get(i), params.get(i));
        }

        return super.exec(scopeStack, scope);
    }
}

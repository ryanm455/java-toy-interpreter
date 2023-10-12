import java.util.ArrayList;
import java.util.Stack;

public class FunctionDef extends Stmt {
    final String fnName;
    final ArrayList<String> params;
    final ArrayList<Stmt> block;

    public FunctionDef(String fnName, ArrayList<String> params, ArrayList<Stmt> block) {
        this.fnName = fnName;
        this.params = params;
        this.block = block;
    }

    @Override
    public Object exec(Stack<Scope> scopeStack) {
        FunctionInst fnInst = new FunctionInst(this.params, this.block);
        scopeStack.peek().defineFunction(fnName, fnInst);
        return super.exec(scopeStack);
    }
}

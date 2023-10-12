import java.util.Stack;

public class LiteralExpr extends Expr {
    final String value;
    final LiteralType literalType;
    public LiteralExpr(String value, LiteralType literalType) {
        this.value = value;
        this.literalType = literalType;
    }

    @Override
    public Integer exec(Stack<Scope> scopeStack) {
        return switch (literalType) {
            case VARIABLE -> scopeStack.peek().getVar(value);
            case INT -> Integer.parseInt(value);
        };
    }
}

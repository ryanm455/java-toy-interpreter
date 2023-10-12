public class Token {
    final String value;
    final TokenType tokenType;
    final int currLine;
    final int start;
    final String sourceLine;

    public Token(String value, TokenType tokenType, int currLine, int start, String sourceLine) {
        this.value = value;
        this.tokenType = tokenType;
        this.currLine = currLine;
        this.start = start;
        this.sourceLine = sourceLine;
    }

    @Override
    public String toString() {
        return tokenType.toString();
    }

    public LiteralType toLiteral() {
        return switch (this.tokenType) {
            case IDENTIFIER -> LiteralType.VARIABLE;
            case INT -> LiteralType.INT;
            case STR -> LiteralType.STR;
            default -> throw new SyntaxError(this, TokenType.IDENTIFIER, TokenType.INT);
        };
    }

    public Op toOp() {
        return switch (this.tokenType) {
            case EQ -> Op.EQ;
            case NOT_EQ -> Op.NOT_EQ;
            case PLUS -> Op.PLUS;
            case MINUS -> Op.MINUS;
            case MULT -> Op.MULT;
            case DIV -> Op.DIV;
            case MOD -> Op.MOD;
            default ->
                    throw new SyntaxError(this, TokenType.NOT_EQ, TokenType.PLUS, TokenType.MINUS, TokenType.MULT, TokenType.DIV);
        };
    }
}



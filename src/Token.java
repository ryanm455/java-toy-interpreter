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
            default -> throw new SyntaxError(this, TokenType.IDENTIFIER, TokenType.INT);
        };
    }

    public Op toOp() {
        if (this.tokenType == TokenType.NOT_EQ) return Op.NOT_EQ;
        throw new SyntaxError(this, TokenType.NOT_EQ);
    }
}



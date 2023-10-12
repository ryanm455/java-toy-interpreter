import java.util.ArrayList;
import java.util.Arrays;
public class Parser {
    private final ArrayList<Stmt> parsedStmts;
    private final ArrayList<Token> tokens;
    private int currTokenNum = 0;

    private Token getCurrToken() {
        return tokens.get(currTokenNum);
    }
    private Token getLastToken() {
        return tokens.get(currTokenNum - 1);
    }
    private Token advance() {
        if (currTokenNum >= tokens.size()) throw new EOFError();
        currTokenNum += 1;
        return getLastToken();
    }
    private boolean match(TokenType... tokens) {
        boolean isMatch = Arrays.stream(tokens).anyMatch(tokTy -> getCurrToken().tokenType == tokTy);
        if (isMatch) advance();
        return isMatch;
    }
    private void consume(TokenType... tokens) {
        for (TokenType token : tokens) if (!match(token)) {
            throw new SyntaxError(getCurrToken(), token);
        }
    }
    private Expr parsePrimaryExpr() {
        if (match(TokenType.IDENTIFIER, TokenType.INT))
            return new LiteralExpr(getLastToken().value, getLastToken().toLiteral());
        else throw new SyntaxError(getCurrToken(), TokenType.IDENTIFIER, TokenType.INT);
    }
    private Expr parseExpr() {
        Expr LHS = parsePrimaryExpr();
        Op op = advance().toOp();
        Expr RHS = parsePrimaryExpr();
        return new BinaryExpr(LHS, op, RHS);
    }
    private String parseVariableName() {
        if (match(TokenType.IDENTIFIER)) {
            return getLastToken().value;
        } else {
            throw new SyntaxError(getCurrToken(), TokenType.IDENTIFIER);
        }
    }
    private void parse() {
        loop: while (currTokenNum < tokens.size()) {
            switch (advance().tokenType) {
                case CLEAR:
                    parsedStmts.add(new Clear(parseVariableName()));
                    break;
                case INCR:
                    parsedStmts.add(new Incr(parseVariableName()));
                    break;
                case DECR:
                    parsedStmts.add(new Decr(parseVariableName()));
                    break;
                case WHILE:
                    Expr expr = parseExpr();
                    consume(TokenType.DO, TokenType.SEMI);
                    Parser parser = new Parser(tokens, currTokenNum);
                    currTokenNum = parser.getCurrTokenNum();
                    parsedStmts.add(new While(expr, parser.getParsedStmts()));
                    break;
                case END:
                    break loop;
                default:
                    throw new SyntaxError(getLastToken(), TokenType.CLEAR, TokenType.INCR, TokenType.DECR, TokenType.WHILE, TokenType.END);

            }
            consume(TokenType.SEMI);
        }
    }
    public int getCurrTokenNum() {
        return currTokenNum;
    }
    public ArrayList<Stmt> getParsedStmts() {
        return parsedStmts;
    }

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        parsedStmts = new ArrayList<>();
        parse();
    }
    public Parser(ArrayList<Token> tokens, int currTokenNum) {
        this.tokens = tokens;
        this.currTokenNum = currTokenNum;
        parsedStmts = new ArrayList<>();
        parse();
    }
}
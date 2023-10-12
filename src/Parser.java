import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Supplier;

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

    private Token advance(int amount) {
        if (currTokenNum + amount > tokens.size()) throw new EOFError();
        currTokenNum += amount;
        return getLastToken();
    }

    private Token advance() {
        return advance(1);
    }

    private boolean match(TokenType... tokens) {
        boolean isMatch = Arrays.stream(tokens).anyMatch(tokTy -> getCurrToken().tokenType == tokTy);
        if (isMatch) advance();
        return isMatch;
    }

    private void consume(TokenType... tokens) {
        for (TokenType token : tokens)
            if (!match(token)) {
                throw new SyntaxError(getCurrToken(), token);
            }
    }

    private boolean peek(int offset, TokenType... tokens) {
        TokenType tokTyAtOffset = this.tokens.get(currTokenNum + offset).tokenType;
        return Arrays.stream(tokens).anyMatch(tokTy -> tokTyAtOffset == tokTy);
    }

    private Expr parseMultiplicativeExpr() {
        Expr LHS = parsePrimaryExpr();
        while (match(TokenType.MULT, TokenType.DIV, TokenType.MOD)) {
            Op op = getLastToken().toOp();
            Expr RHS = parsePrimaryExpr();
            LHS = new BinaryExpr(LHS, op, RHS);
        }
        return LHS;
    }

    private Expr parseAdditiveExpr() {
        Expr LHS = parseMultiplicativeExpr();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Op op = getLastToken().toOp();
            Expr RHS = parseMultiplicativeExpr();
            LHS = new BinaryExpr(LHS, op, RHS);
        }
        return LHS;
    }

    private Expr parsePrimaryExpr() {
        if (peek(0, TokenType.IDENTIFIER, TokenType.INT, TokenType.STR)) {
            if (peek(1, TokenType.LPAREN)) return parseFnCall();
            else {
                advance();
                return new LiteralExpr(getLastToken().value, getLastToken().toLiteral());
            }
        } else if (match(TokenType.LPAREN)) {
            Expr expr = parseExpr();
            consume(TokenType.RPAREN);
            return expr;
        } else {
            throw new SyntaxError(getCurrToken(), TokenType.IDENTIFIER, TokenType.INT, TokenType.STR, TokenType.LPAREN);
        }
    }

    private Expr parseExpr() {
        Expr LHS = parseAdditiveExpr();
        while (match(TokenType.EQ, TokenType.NOT_EQ)) {
            Op op = getLastToken().toOp();
            Expr RHS = parseAdditiveExpr();
            LHS = new BinaryExpr(LHS, op, RHS);
        }
        return LHS;
    }

    private FunctionCallExpr parseFnCall() {
        String fnName = getCurrToken().value;
        consume(TokenType.IDENTIFIER, TokenType.LPAREN);
        ArrayList<Expr> params = new ArrayList<>();
        while (!match(TokenType.RPAREN)) {
            params.add(parseExpr());
        }
        return new FunctionCallExpr(fnName, params);
    }

    private ArrayList<Stmt> parseBlock() {
        Parser parser = new Parser(this.tokens, this.currTokenNum);
        currTokenNum = parser.getCurrTokenNum();
        return parser.getParsedStmts();
    }


    private String parseVariableName() {
        if (match(TokenType.IDENTIFIER)) {
            return getLastToken().value;
        } else {
            throw new SyntaxError(getCurrToken(), TokenType.IDENTIFIER);
        }
    }

    private If parseIf() {
        if (match(TokenType.IF, TokenType.ELSE_IF)) {
            Expr ifCond = parseExpr();
            consume(TokenType.DO, TokenType.SEMI);
            ArrayList<Stmt> block = parseBlock();

            If ifBlock = new If(ifCond, block);

            if (match(TokenType.ELSE)) {
                consume(TokenType.SEMI);
                ifBlock.chain(new Block(parseBlock()));
            } else if (peek(0, TokenType.ELSE_IF)) {
                ifBlock.chain(parseIf());
            }

            return ifBlock;
        }

        throw new SyntaxError(getCurrToken(), TokenType.IF, TokenType.ELSE_IF);
    }

    private void parse() {
        loop:
        while (currTokenNum < tokens.size()) {
            switch (getCurrToken().tokenType) {
                case DECR, INCR, CLEAR:
                    advance();
                    Map<TokenType, Supplier<Stmt>> statementSuppliers = Map.of(
                            TokenType.CLEAR, () -> new Clear(parseVariableName()),
                            TokenType.INCR, () -> new Incr(parseVariableName()),
                            TokenType.DECR, () -> new Decr(parseVariableName())
                    );
                    parsedStmts.add(statementSuppliers.get(getCurrToken().tokenType).get());
                    break;
                case PRINT:
                    advance();
                    parsedStmts.add(new Print(parseExpr()));
                    break;
                case IDENTIFIER:
                    String varName = getCurrToken().value;
                    if (peek(1, TokenType.ASSIGN)) {
                        advance(2);
                        Expr value = parseExpr();
                        parsedStmts.add(new Assign(varName, value));
                    } else {
                        parsedStmts.add(new ExprStmt(parseFnCall()));
                    }
                    break;
                case WHILE:
                    advance();
                    Expr cond = parseExpr();
                    consume(TokenType.DO, TokenType.SEMI);
                    parsedStmts.add(new While(cond, parseBlock()));
                    break;
                case END:
                    advance();
                    break loop;
                case DEF:
                    advance();
                    String fnName = parseVariableName();
                    consume(TokenType.LPAREN);

                    ArrayList<String> params = new ArrayList<>();
                    while (!match(TokenType.RPAREN)) {
                        params.add(parseVariableName());
                    }

                    consume(TokenType.SEMI);
                    parsedStmts.add(new FunctionDef(fnName, params, parseBlock()));

                    break;
                case IF:
                    parsedStmts.add(parseIf());
                    break;
                case ELSE, ELSE_IF:
                    // TODO! prevent these from being used without an if.
                    break loop;
                case RETURN:
                    advance();
                    parsedStmts.add(new Return(parseExpr()));
                    break;
                default:
                    parsedStmts.add(new ExprStmt(parseExpr()));

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
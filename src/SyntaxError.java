import java.util.Arrays;

public class SyntaxError extends RuntimeException {
    private final int line;
    private final String sourceLine;
    private final String errorMarker;
    private final String expectation;

    public SyntaxError(Token found, TokenType... expected) {
        super("");
        line = found.currLine;
        sourceLine = found.sourceLine;
        errorMarker = generateErrorMarker(sourceLine, found.start, found.start + found.value.length());
        expectation = String.format("Found Token: %s, Expected: %s.", found, String.join(", ", Arrays.stream(expected).map(Enum::toString).toList()));
    }

    public SyntaxError(String sourceLine, int currLine) {
        super("");
        line = currLine;
        this.sourceLine = sourceLine;
        errorMarker = generateErrorMarker(sourceLine);
        expectation = null;
    }

    private String generateErrorMarker(String sourceLine) {
        return "^".repeat(sourceLine.length());
    }

    private String generateErrorMarker(String sourceLine, int start, int end) {
        return " ".repeat(sourceLine.length() - end + start - 1) + "^".repeat(end - start + 1);
    }

    @Override
    public String toString() {
        return "Syntax error at line " + line + ".\n" +
                sourceLine + "\n" +
                errorMarker + "\n" +
                (expectation != null ? expectation : "");
    }
}

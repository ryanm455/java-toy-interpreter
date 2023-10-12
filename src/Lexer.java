import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

record Pair<K, V>(K key, V value) {}
public class Lexer {
    private final ArrayList<Token> tokens;
    private static final List<Pair<String, TokenType>> patterns = List.of(
            new Pair<>("decr", TokenType.DECR),
            new Pair<>("incr", TokenType.INCR),
            new Pair<>("clear", TokenType.CLEAR),
            new Pair<>("end", TokenType.END),
            new Pair<>("while", TokenType.WHILE),
            new Pair<>("not", TokenType.NOT_EQ),
            new Pair<>("do", TokenType.DO),
            new Pair<>("\\b[a-zA-Z_][a-zA-Z0-9_]*\\b", TokenType.IDENTIFIER),
            new Pair<>("\\d+", TokenType.INT),
            new Pair<>(";", TokenType.SEMI),
            new Pair<>("\\s+", null)
    );

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public Lexer(String input) {
        tokens = new ArrayList<>();

        String[] lines = input.split("\\n");

        for (int currLine = 1; currLine < lines.length + 1; currLine++) {
            lexLine(lines[currLine-1], currLine);
        }
    }

    private void lexLine(String sourceLine, int currLine) {
        String lineInput = sourceLine;
        while (!lineInput.isEmpty()) {
            boolean foundMatch = false;
            for (Pair<String, TokenType> pattern : patterns) {
                Matcher m = Pattern.compile("^" + pattern.key()).matcher(lineInput);
                if (m.find()) {
                    int start = sourceLine.indexOf(m.group(0));
                    if (pattern.value() != null) tokens.add(new Token(m.group(0), pattern.value(), currLine, start, sourceLine));
                    lineInput = m.replaceFirst("").trim();
                    foundMatch = true;
                }
            }

            if (!foundMatch) {
                throw new SyntaxError(sourceLine, currLine);
            }
        }
    }
}

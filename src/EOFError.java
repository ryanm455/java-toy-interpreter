public class EOFError extends RuntimeException {
    public EOFError() {
        super("Expected token, found end of file.");
    }
}

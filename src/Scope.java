import java.util.HashMap;

public class Scope {
    private final Scope upperScope;
    private final HashMap<String, Integer> variables;
    public Scope() {
        this(null);
    }

    public Scope(Scope upperScope) {
        this.upperScope = upperScope;
        this.variables = new HashMap<>();
    }

    public void assign(String name, Integer val) {
        if (variables.containsKey(name) || !hasVar(name)) {
            variables.put(name, val);
        } else {
            upperScope.assign(name, val);
        }
    }

    public boolean hasVar(String name) {
        return variables.containsKey(name) || (this.upperScope != null && this.upperScope.hasVar(name));
    }

    public Integer getVar(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (upperScope != null) {
            return upperScope.getVar(name);
        } else {
            throw new RuntimeError(String.format("Variable %s is not defined.", name));
        }
    }
}

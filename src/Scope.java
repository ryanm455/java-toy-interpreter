import java.util.HashMap;

public class Scope {
    private final Scope upperScope;
    private final HashMap<String, Integer> variables;

    public Scope() {
       this(null);
    }
    public Scope(Scope upperScope) {
        System.out.println("Created scope.");
        this.upperScope = upperScope;
        this.variables = new HashMap<>();
    }

    public void define(String name, Integer val) {
        variables.put(name, val);
    }

    public void assign(String name, int val) {
        if (variables.containsKey(name)) {
            variables.put(name, val);
        } else if (upperScope != null) {
            upperScope.assign(name, val);
        } else {
            throw new RuntimeError(String.format("Variable %s is not defined.", name));
        }
    }

    public int getVar(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (upperScope != null) {
            return upperScope.getVar(name);
        } else {
            throw new RuntimeError(String.format("Variable %s is not defined.", name));
        }
    }
}

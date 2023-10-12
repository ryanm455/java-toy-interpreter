import java.util.HashMap;

public class Scope {
    private final Scope upperScope;
    private final HashMap<String, Object> variables;
    private final HashMap<String, FunctionInst> functions;

    public Scope() {
        this(null);
    }

    public Scope(Scope upperScope) {
        this.upperScope = upperScope;
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public void assign(String name, Object val) {
        if (variables.containsKey(name) || !hasVar(name)) {
            variables.put(name, val);
        } else {
            upperScope.assign(name, val);
        }
    }

    public boolean hasVar(String name) {
        return variables.containsKey(name) || (this.upperScope != null && this.upperScope.hasVar(name));
    }

    public Object getVar(String name) {
        if (variables.containsKey(name)) {
            return variables.get(name);
        } else if (upperScope != null) {
            return upperScope.getVar(name);
        } else {
            throw new RuntimeError(String.format("Variable %s is not defined.", name));
        }
    }

    public FunctionInst getFunction(String fnName) {
        if (functions.containsKey(fnName)) {
            return functions.get(fnName);
        } else if (upperScope != null) {
            return upperScope.getFunction(fnName);
        } else {
            throw new RuntimeError(String.format("Function %s is not defined.", fnName));
        }
    }

    public void defineFunction(String fnName, FunctionInst inst) {
        functions.put(fnName, inst);
    }
}

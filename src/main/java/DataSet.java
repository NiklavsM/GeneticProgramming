import java.util.List;

public class DataSet {
    List<String> variables;
    List<Instance> instances;


    public DataSet(List<String> variables, List<Instance> instances) {
        this.variables = variables;
        this.instances = instances;
    }

    public List<String> getVariables() {
        return variables;
    }
}

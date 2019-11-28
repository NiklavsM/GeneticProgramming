import java.util.List;

public class DataSet {
    private List<String> variables;
    private List<Instance> instances;


    public DataSet(List<String> variables, List<Instance> instances) {
        this.variables = variables;
        this.instances = instances;
    }

    public List<String> getVariables() {
        return variables;
    }

    public List<Instance> getInstances() {
        return instances;
    }

    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
}

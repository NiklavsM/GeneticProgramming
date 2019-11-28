import java.util.Map;

public class Instance {
    Double effort;
    Map<String, Double> attributes;

    public Instance(Map<String, Double> attributes, Double effort) {
        this.attributes = attributes;
        this.effort = effort;
    }
}

import java.util.Map;

public class Instance {
    private Double effort;
    private Map<String, Double> attributes;

    public Instance(Map<String, Double> attributes, Double effort) {
        this.attributes = attributes;
        this.effort = effort;
    }

    public Double getEffort() {
        return effort;
    }

    public void setEffort(Double effort) {
        this.effort = effort;
    }

    public Map<String, Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Double> attributes) {
        this.attributes = attributes;
    }
}

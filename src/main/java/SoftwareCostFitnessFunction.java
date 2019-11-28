import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

import java.util.List;

public class SoftwareCostFitnessFunction extends GPFitnessFunction {

    private DataSet dataSet;
    private List<Variable> variables;

    private static Object[] NO_ARGS = new Object[0];

    public SoftwareCostFitnessFunction(List<Variable> variables, DataSet dataSet) {
        this.variables = variables;
        this.dataSet = dataSet;
    }

    @Override
    protected double evaluate(final IGPProgram program) {

        double longResult = 0;
        for (Instance instance : dataSet.getInstances()) {
            // Set the input values
            for (Variable variable : variables) {
                variable.set(instance.getAttributes().get(variable.getName()));
            }
            // Execute the genetically engineered algorithm
            double value = program.execute_double(0, NO_ARGS);

            // The closer longResult gets to 0 the better the algorithm.
            longResult += Math.abs(value - instance.getEffort()) / instance.getEffort() * 100;
//            System.out.println("instance.effort " + instance.effort);
        }

        return longResult / dataSet.getInstances().size();
    }

}
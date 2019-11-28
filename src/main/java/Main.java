import org.jgap.InvalidConfigurationException;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.GPGenotype;

public class Main {



    public static void main(String[] args) {
        double testingRatio = 0.1;
        FileLoader fl = new FileLoader();
        DataSet ds = fl.getDataSet(false, 1-testingRatio);
        ds.getVariables().forEach(System.out::println);
        ds.instances.forEach(instance -> System.out.println(instance.attributes.values().toString()));
        try {
            GPProblem problem = new SoftwareCostEstimation(ds);

            GPGenotype gp = problem.create();
            gp.setVerboseOutput(true);
            gp.evolve(900);

            System.out.println("Effort = ");
            gp.outputSolution(gp.getAllTimeBest());
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

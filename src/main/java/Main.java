import org.jgap.InvalidConfigurationException;
import org.jgap.gp.GPProblem;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        FileLoader fl = new FileLoader();
        Scanner sc = new Scanner(System.in);
        DataSet ds;
        System.out.println("Select dataset:");
        System.out.println("1: albrecht.arff");
        System.out.println("2: china.arrt");
        switch (sc.nextLine()) {
            case "1":
                ds = fl.getDataSet("/albrecht.arff");
                break;
            case "2":
                ds = fl.getDataSet("/china.arff");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sc.nextLine());
        }
        double testingRatio = 0.2;

        DataSet dsValidate = new DataSet(ds.getVariables(), ds.getInstances().subList(0, (int) (ds.getInstances().size() * testingRatio))); // Dataset used for model validation 0.2
        DataSet dsTrain = new DataSet(ds.getVariables(), ds.getInstances().subList((int) (ds.getInstances().size() * testingRatio), ds.getInstances().size())); // Dataset used for model training 0.8

        try {
            GPProblem problem = new SoftwareCostEstimationProblem(dsTrain);

            GPGenotype gp = problem.create();
            gp.setVerboseOutput(true);
            gp.evolve(500);
            gp.outputSolution(gp.getAllTimeBest());

            validate(gp.getAllTimeBest(), dsValidate);
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void validate(IGPProgram gp, DataSet dsValidate) {
        double mrae = 0.0; // Mean Relative Absolute Error
        for (Instance i : dsValidate.getInstances()) {
            int varIndex = 0;
            for (String v : dsValidate.getVariables()) {
                ((Variable) gp.getNodeSets()[0][varIndex]).set(i.getAttributes().get(v));
                varIndex++;
            }
            double val = gp.execute_double(0, new Object[]{});
            mrae += (Math.abs(i.getEffort() - val) / i.getEffort() * 100);

        }
        System.out.println("Mean Relative Absolute Error " + mrae / dsValidate.getInstances().size() + " %");
    }
}

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.*;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

import java.util.LinkedList;
import java.util.List;

public class SoftwareCostEstimation extends GPProblem {

    private List<Variable> variables;

    public SoftwareCostEstimation(DataSet ds) throws InvalidConfigurationException {
        super(new GPConfiguration());
        GPConfiguration config = getGPConfiguration();
        variables = new LinkedList<>();
        for (String name : ds.getVariables()) {
            variables.add(Variable.create(config, name, CommandGene.DoubleClass));
        }


        config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
        config.setMaxInitDepth(20);
        config.setPopulationSize(1000);
        config.setMaxCrossoverDepth(40);
        config.setFitnessFunction(new SoftwareCostFitnessFunction(variables, ds));
        config.setStrictProgramCreation(true);
    }

    @Override
    public GPGenotype create() throws InvalidConfigurationException {
        GPConfiguration config = getGPConfiguration();

        // The return type of the GP program.
        Class[] types = {CommandGene.DoubleClass};

        // Arguments of result-producing chromosome: none
        Class[][] argTypes = {{}};

        CommandGene[] funcNodes = {
                new Add(config, CommandGene.DoubleClass),
                new Multiply(config, CommandGene.DoubleClass),
                new Subtract(config, CommandGene.DoubleClass),
                new Divide(config, CommandGene.DoubleClass),
//                new Pow(config, CommandGene.DoubleClass),
//                new Log(config, CommandGene.DoubleClass),
                new Terminal(config, CommandGene.DoubleClass, 0.0, 100, false)
        };

        CommandGene[] varNodes = new CommandGene[variables.size()];
        variables.toArray(varNodes);
        int i = 0;

        CommandGene[] allNodes = new CommandGene[funcNodes.length + varNodes.length];
        System.arraycopy(varNodes, 0, allNodes, 0, varNodes.length);
        System.arraycopy(funcNodes, 0, allNodes, varNodes.length, funcNodes.length);

        // Next, we define the set of available GP commands and terminals to
        // use.
        CommandGene[][] nodeSets = {
                allNodes
        };

        GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes,
                nodeSets, 20, true);

        return result;
    }

//    public static void main(String[] args) throws Exception {
//        GPProblem problem = new SoftwareCostEstimation();
//
//        GPGenotype gp = problem.create();
//        gp.setVerboseOutput(true);
//        gp.evolve(30);
//
//        System.out.println("Formula to discover: x^2 + 2y + 3x + 5");
//        gp.outputSolution(gp.getAllTimeBest());
//    }

}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileLoader {
    public DataSet getDataSet(boolean forTesting, double testingRation) {
        DataSet ds;
        Scanner scanner = null;
        List<String> attributes = new ArrayList<>();
        List<Instance> instances = new ArrayList<>();
        try {
            scanner = new Scanner(new File("C:/Users/Niklavs/Documents/IntelliJProjects/Genetic Programming/src/main/resources/albrecht.arff"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("@attribute") && !line.startsWith("@attribute Effort")) {
                    String[] tokens = line.split(" ");
                    attributes.add(tokens[1]);
                    System.out.println(line);
                }

                if (line.startsWith("@data")) {
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        String[] tokens = line.split(",");
                        Map<String, Double> attributeValues = new HashMap<>();
                        for (int i = 0; i < tokens.length; i++) {
                            if (i == tokens.length - 1) {
                                instances.add(new Instance(attributeValues, Double.valueOf(tokens[i])));
                            }else {
                                attributeValues.put(attributes.get(i), Double.valueOf(tokens[i]));
                            }
                        }
                    }
                }
            }
            System.out.println(instances.size() + "  " + attributes.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return new DataSet(attributes, instances);
    }
}

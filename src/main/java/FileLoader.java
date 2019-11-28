import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileLoader {
    public DataSet getDataSet(String file) {
        DataSet ds;
        Scanner scanner;
        List<String> attributes = new ArrayList<>();
        List<Instance> instances = new ArrayList<>();
        try {
            scanner = new Scanner(new File("C:/Users/Niklavs/Documents/IntelliJProjects/Genetic Programming/src/main/resources/" + file)); // Relative path would be nicer
            boolean hasId = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!hasId) {
                    hasId = line.startsWith("@attribute ID");
                }
                if (line.startsWith("@attribute") && !line.startsWith("@attribute Effort") && !line.startsWith("@attribute ID")) {
                    String[] tokens = line.split(" ");
                    attributes.add(tokens[1]);
                    System.out.println(line);
                }

                if (line.startsWith("@data")) {
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        String[] tokens = line.split(",");
                        Map<String, Double> attributeValues = new HashMap<>();
                        int i;
                        if (hasId) {
                            for (i = 1; i < tokens.length - 1; i++) {
                                attributeValues.put(attributes.get(i - 1), Double.valueOf(tokens[i]));
                                System.out.println(tokens.length + " " + attributes.size());
                            }
                        } else {
                            for (i = 0; i < tokens.length - 1; i++) {
                                attributeValues.put(attributes.get(i), Double.valueOf(tokens[i]));
                            }
                        }

                        instances.add(new Instance(attributeValues, Double.valueOf(tokens[tokens.length - 1])));
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

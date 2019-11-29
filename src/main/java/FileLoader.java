import java.io.InputStream;
import java.util.*;

public class FileLoader {
    public DataSet getDataSet(String file) {
        Scanner scanner;
        List<String> attributes = new ArrayList<>();
        List<Instance> instances = new ArrayList<>();
        InputStream inputStream = getClass().getResourceAsStream(file);
        scanner = new Scanner(inputStream); // Relative path would be nicer
        boolean hasId = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!hasId) {
                hasId = line.startsWith("@attribute ID");
            }
            if (line.startsWith("@attribute")) {
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
                        attributeValues.put(attributes.get(i), Double.valueOf(tokens[i]));
                    }
                    attributeValues.remove("Effort");
                    attributeValues.remove("ID");
                    instances.add(new Instance(attributeValues, Double.valueOf(tokens[tokens.length - 1])));
                }
                attributes.remove("Effort");
                attributes.remove("ID");
            }
        }
        return new DataSet(attributes, instances);
    }
}

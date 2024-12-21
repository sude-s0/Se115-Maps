import java.util.Scanner;
import java.io.File;
import java.io.IOException;
public class CountryMap {
    private String[] cityNames;
    private int[][] routes;
    private int cityCount;
    private int routeCount;

    public void loadMap(String inputFile) {
        String[] lines = readFile(inputFile).split("\n");
        cityCount = Integer.parseInt(lines[0]);
        cityNames = lines[1].split(" ");
        routeCount = Integer.parseInt(lines[2]);
        routes = new int[cityCount][cityCount];

        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                routes[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int i = 0; i < routeCount; i++) {
            String[] route = lines[3 + i].split(" ");
            int city1 = getCityIndex(route[0]);
            int city2 = getCityIndex(route[1]);
            int time = Integer.parseInt(route[2]);
            routes[city1][city2] = time;
            routes[city2][city1] = time;
        }
    }
    public String readFile(String fileName) {
        String content = "";
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                content += scanner.nextLine() + "\n";
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return content.trim();
}

    public int getCityIndex(String name) {
        for (int i = 0; i < cityNames.length; i++) {
            if (cityNames[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }

    public int[][] getRoutes() {
        return routes;
    }

    public String[] getCityNames() {
        return cityNames;
    }
}

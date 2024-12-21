import java.io.FileWriter;
import java.io.IOException;

public class WayFinder {
    private int[] shortestPath;
    private int totalTime;

    public void findShortestPath(int[][] routes, String[] cityNames, String startCity, String endCity) {
        int start = getCityIndex(cityNames, startCity);
        int end = getCityIndex(cityNames, endCity);

        if (start == -1 || end == -1) {
            System.out.println("Error: Invalid city name(s) provided.");
            return;
        }

        int cityCount = cityNames.length;
        boolean[] visited = new boolean[cityCount];
        int[] distances = new int[cityCount];
        int[] previous = new int[cityCount];

        for (int i = 0; i < cityCount; i++) {
            distances[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }
        distances[start] = 0;

        for (int i = 0; i < cityCount; i++) {
            int currentCity = findClosestCity(distances, visited);
            if (currentCity == -1) break;

            visited[currentCity] = true;
            for (int neighbor = 0; neighbor < cityCount; neighbor++) {
                if (routes[currentCity][neighbor] != Integer.MAX_VALUE && !visited[neighbor]) {
                    int newDist = distances[currentCity] + routes[currentCity][neighbor];
                    if (newDist < distances[neighbor]) {
                        distances[neighbor] = newDist;
                        previous[neighbor] = currentCity;
                    }
                }
            }
        }

        totalTime = distances[end];
        if (totalTime == Integer.MAX_VALUE) {
            System.out.println("No path exists between " + startCity + " and " + endCity + ".");
            totalTime = -1;
            return;
        }

        buildPath(previous, end);
    }

    private int findClosestCity(int[] distances, boolean[] visited) {
        int minDist = Integer.MAX_VALUE;
        int minCity = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDist) {
                minDist = distances[i];
                minCity = i;
            }
        }
        return minCity;
    }

    private void buildPath(int[] previous, int end) {
        int[] tempPath = new int[previous.length];
        int pathLength = 0;

        // Saving the route in the opposite way
        for (int i = end; i != -1; i = previous[i]) {
            tempPath[pathLength++] = i;
        }

        // Turning the route on the right side
        shortestPath = new int[pathLength];
        for (int i = 0; i < pathLength; i++) {
            shortestPath[i] = tempPath[pathLength - 1 - i];
        }
    }

    private int getCityIndex(String[] cityNames, String city) {
        for (int i = 0; i < cityNames.length; i++) {
            if (cityNames[i].equals(city)) {
                return i;
            }
        }
        return -1;
    }

    public void writeResult(String outputFile, String[] cityNames) {
        try {
            FileWriter writer = new FileWriter(outputFile);
            writer.write("Fastest Way: ");
            for (int i = 0; i < shortestPath.length; i++) {
                writer.write(cityNames[shortestPath[i]]);
                if (i < shortestPath.length - 1) writer.write(" -> ");
            }
            writer.write("\nTotal Time: " + totalTime + " min");
            writer.close();
        } catch (IOException exception) {
            System.out.println("An error occurred while writing the file: " + exception.getMessage());
        }
    }
}

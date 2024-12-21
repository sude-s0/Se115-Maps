class WayFinder {
    private int[] shortestPath;
    private int totalTime;
    public void findShortestPath(int[][] routes, String[] cityNames, String startCity, String endCity) {
        int start = getCityIndex(cityNames, startCity);
        int end = getCityIndex(cityNames, endCity);

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
        buildPath(previous, start, end);
    }
//this funtion allows us to find the closest city
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
    private void buildPath(int[] previous, int start, int end) {
        java.util.Stack<Integer> stack = new java.util.Stack<>();
        for (int at = end; at != -1; at = previous[at]) {
            stack.push(at);
        }

        shortestPath = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            shortestPath[i++] = stack.pop();
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
        java.io.PrintWriter writer = null;
        writer = new java.io.PrintWriter(outputFile);
        writer.print("Fastest Way: ");
        for (int i = 0; i < shortestPath.length; i++) {
            writer.print(cityNames[shortestPath[i]]);
            if (i < shortestPath.length - 1) writer.print(" -> ");
        }
        writer.println("\nTotal Time: " + totalTime + " min");
        writer.close();
    }
}

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

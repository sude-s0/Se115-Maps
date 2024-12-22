import java.io.FileWriter;
import java.io.IOException;

public class WayFinder {
    private int[] shortestPath; //En kısa yolun indexi
    private int totalTime; //Toplam süre
    
// Dijkstra algoritması kullanıldı
    public void findShortestPath(int[][] routes, String[] cityNames, String startCity, String endCity) {
        int start = getCityIndex(cityNames, startCity); //Başlangıç şehrinin indexi
        int end = getCityIndex(cityNames, endCity); //Bitiş şehrinin indexi

        if (start == -1 || end == -1) { //Geçersiz şehir ismi girilirse diye kontrol
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
        distances[start] = 0; //Başlangıç şehrinin mesafesi 0

        for (int i = 0; i < cityCount; i++) {
            int currentCity = findClosestCity(distances, visited); //En yakın şehri bul
            if (currentCity == -1) break;

            visited[currentCity] = true;
            for (int neighbor = 0; neighbor < cityCount; neighbor++) {
                if (routes[currentCity][neighbor] != Integer.MAX_VALUE && !visited[neighbor]) {
                    int newDist = distances[currentCity] + routes[currentCity][neighbor];
                    if (newDist < distances[neighbor]) { //Daha kısa bir yol bulunduysa
                        distances[neighbor] = newDist;
                        previous[neighbor] = currentCity;
                    }
                }
            }
        }

        totalTime = distances[end]; // Bitiş şehrine olan toplam süre
        if (totalTime == Integer.MAX_VALUE) {
            System.out.println("No path exists between " + startCity + " and " + endCity + ".");
            totalTime = -1;
            return;
        }

        buildPath(previous, end); //En kısa yolu oluştur
    }

    private int findClosestCity(int[] distances, boolean[] visited) {
        int minDist = Integer.MAX_VALUE;
        int minCity = -1;
        for (int i = 0; i < distances.length; i++) {
            if (!visited[i] && distances[i] < minDist) { // Ziyaret edilmemiş en kısa mesafeli şehir
                minDist = distances[i];
                minCity = i;
            }
        }
        return minCity;
    }

    private void buildPath(int[] previous, int end) {
        int[] tempPath = new int[previous.length];
        int pathLength = 0;

        // Yolu ters sırayla kaydeder
        for (int i = end; i != -1; i = previous[i]) {
            tempPath[pathLength++] = i;
        }
        // Yolu doğru sıraya çevirir
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
        return -1; // Şehir bulunamadıysa -1 döndürür
    }

    public void writeResult(String outputFile, String[] cityNames) {
        try {
            FileWriter writer = new FileWriter(outputFile); // Çıktı dosyasını açar
            writer.write("Fastest Way: ");
            for (int i = 0; i < shortestPath.length; i++) {
                writer.write(cityNames[shortestPath[i]]); // Şehir isimlerini yazar
                if (i < shortestPath.length - 1) writer.write(" -> ");
            }
            writer.write("\nTotal Time: " + totalTime + " min");  // Toplam süreyi yazar
            writer.close(); // Dosyayı kapatır
        } catch (IOException exception) {
            System.out.println("An error occurred while writing the file: " + exception.getMessage());
        }
    }
}

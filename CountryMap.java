import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class CountryMap {
    private String[] cityNames;
    private int[][] routes;
    private int cityCount;
    private int routeCount;

    public void loadMap(String inputFile) {
        String[] lines = readFile(inputFile).split("\n"); // Dosyadan okunan satırları bölüyor
        cityCount = Integer.parseInt(lines[0]); // İlk satır şehir sayısını içeriyor
        cityNames = lines[1].split(" "); // İkinci satır şehir isimlerini içeriyor
        routeCount = Integer.parseInt(lines[2]); // Üçüncü satır yol sayısını içeriyor
        routes = new int[cityCount][cityCount];

        // Rotalar matrisini başlangıçta maksimum değerle dolduruyor
        for (int i = 0; i < cityCount; i++) {
            for (int j = 0; j < cityCount; j++) {
                routes[i][j] = Integer.MAX_VALUE;
            }
        }
        // Yol bilgilerini matrise ekliyor
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
        String content = ""; // Dosya içeriğini tutacak değişken
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                content += scanner.nextLine() + "\n"; // Her satırı içeriğe ekle
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage()); // Hata durumunda yazılacak mesaj
        }
        return content.trim(); // Sonuçtan boşlukları temizliyor
}

    public int getCityIndex(String name) {
        for (int i = 0; i < cityNames.length; i++) {
            if (cityNames[i].equals(name)) { // Şehir ismi eşleşiyorsa indeksini döndür
                return i;
            }
        }
        return -1; // Şehir bulunamadıysa -1 döndürür
    }

    public int[][] getRoutes() {
        return routes; // Şehirler arası rotalar matrisini döndür
    }

    public String[] getCityNames() {
        return cityNames; // Şehir isimlerini döndür
    } 
}

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CountryMap map = new CountryMap(); //Harita verilerini yüklemek için
        map.loadMap("input.txt");

        WayFinder finder = new WayFinder(); //En kısa yolu bulmak için oluşturduğum class

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the start city: "); //Başlangıç şehri alınıyor
        String startCity = scanner.next();
        System.out.print("Enter the end city: "); //Bitiş şehri alınıyor
        String endCity = scanner.next();

        finder.findShortestPath(map.getRoutes(), map.getCityNames(), startCity, endCity);
        finder.writeResult("output.txt", map.getCityNames()); //Sonuçları dosyaya yazdırıyor
        
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); //Dosya içeriğini ekrana yazdırıyor
            }
        } catch (IOException e) {
            System.out.println("Error reading file"); //Hata olursa yazılıcak mesaj
        }
    }
}

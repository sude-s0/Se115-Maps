import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CountryMap map = new CountryMap();
        map.loadMap("input.txt");

        WayFinder finder = new WayFinder();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the start city: ");
        String startCity = scanner.next();
        System.out.print("Enter the end city: ");
        String endCity = scanner.next();

        finder.findShortestPath(map.getRoutes(), map.getCityNames(), startCity, endCity);
        finder.writeResult("output.txt", map.getCityNames());
        
        try (BufferedReader reader = new BufferedReader(new FileReader("output.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}

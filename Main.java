import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CountryMap map = new CountryMap();
        map.loadMap("input.txt");

        WayFinder finder = new WayFinder();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the start city: ");
        String startCity = scanner.next();
        System.out.println("Enter the end city: ");
        String endCity = scanner.next();

        finder.findShortestPath(map.getRoutes(), map.getCityNames(), startCity, endCity);
        finder.writeResult("output.txt", map.getCityNames()); }
}

public class Main {
    public static void main(String[] args) {
        CountryMap map = new CountryMap();
        map.loadMap("input.txt");

        WayFinder finder = new WayFinder();
        finder.findShortestPath(map.getRoutes(), map.getCityNames(), "A", "E");
        finder.writeResult("output.txt", map.getCityNames());
    }
}

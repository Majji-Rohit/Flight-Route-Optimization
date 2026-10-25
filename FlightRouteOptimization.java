
import java.util.*;

public class FlightRouteOptimization {

    static class Edge {
        String destination;
        int distance;

        Edge(String destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }

    static class Node {
        String airport;
        int distance;

        Node(String airport, int distance) {
            this.airport = airport;
            this.distance = distance;
        }
    }

    static class Graph {
        private final Map<String, List<Edge>> adjList = new LinkedHashMap<>();

        void addAirport(String airport) {
            adjList.putIfAbsent(airport.toUpperCase(), new ArrayList<>());
        }

        void addFlight(String source, String destination, int distance) {
            source = source.toUpperCase();
            destination = destination.toUpperCase();

            if (distance <= 0) {
                throw new IllegalArgumentException(
                    "Distance must be positive."
                );
            }

            if (!adjList.containsKey(source)
                    || !adjList.containsKey(destination)) {
                throw new IllegalArgumentException(
                    "Airport does not exist."
                );
            }

            adjList.get(source).add(new Edge(destination, distance));
            adjList.get(destination).add(new Edge(source, distance));
        }

        void displayAirports() {
            System.out.println("\nAvailable Airports:");

            for (String airport : adjList.keySet()) {
                System.out.println("- " + airport);
            }
        }

        void displayFlights() {
            System.out.println("\nAvailable Flight Routes:");

            for (String airport : adjList.keySet()) {
                for (Edge edge : adjList.get(airport)) {
                    if (airport.compareTo(edge.destination) < 0) {
                        System.out.println(
                            airport + " <-> " + edge.destination
                            + " : " + edge.distance + " km"
                        );
                    }
                }
            }
        }

        void findShortestRoute(String source, String destination) {
            source = source.toUpperCase();
            destination = destination.toUpperCase();

            if (!adjList.containsKey(source)
                    || !adjList.containsKey(destination)) {
                System.out.println("Invalid airport code.");
                return;
            }

            Map<String, Integer> distances = new HashMap<>();
            Map<String, String> parent = new HashMap<>();

            for (String airport : adjList.keySet()) {
                distances.put(airport, Integer.MAX_VALUE);
            }

            PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingInt(node -> node.distance)
            );

            distances.put(source, 0);
            pq.offer(new Node(source, 0));

            while (!pq.isEmpty()) {
                Node current = pq.poll();
                String airport = current.airport;

                if (current.distance != distances.get(airport)) {
                    continue;
                }

                if (airport.equals(destination)) {
                    break;
                }

                for (Edge edge : adjList.get(airport)) {
                    long newDistance =
                        (long) current.distance + edge.distance;

                    if (newDistance < distances.get(edge.destination)) {
                        distances.put(
                            edge.destination, (int) newDistance
                        );

                        parent.put(edge.destination, airport);

                        pq.offer(new Node(
                            edge.destination, (int) newDistance
                        ));
                    }
                }
            }

            if (distances.get(destination) == Integer.MAX_VALUE) {
                System.out.println("No route available between "
                    + source + " and " + destination);
                return;
            }

            List<String> path = new ArrayList<>();
            String current = destination;

            while (current != null) {
                path.add(current);

                if (current.equals(source)) {
                    break;
                }

                current = parent.get(current);
            }

            Collections.reverse(path);

            System.out.println("\n===== OPTIMAL FLIGHT ROUTE =====");
            System.out.println("Source: " + source);
            System.out.println("Destination: " + destination);
            System.out.println("Shortest Distance: "
                + distances.get(destination) + " km");

            System.out.print("Route: ");

            for (int i = 0; i < path.size(); i++) {
                System.out.print(path.get(i));

                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println("\nNumber of Flights: "
                + (path.size() - 1));
            System.out.println("================================");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph graph = new Graph();

        String[] airports = {
            "DEL", "BOM", "BLR", "HYD", "MAA", "CCU", "COK"
        };

        for (String airport : airports) {
            graph.addAirport(airport);
        }

        graph.addFlight("DEL", "BOM", 1150);
        graph.addFlight("DEL", "BLR", 1700);
        graph.addFlight("DEL", "HYD", 1250);
        graph.addFlight("BOM", "BLR", 840);
        graph.addFlight("BOM", "HYD", 620);
        graph.addFlight("BOM", "CCU", 1650);
        graph.addFlight("BLR", "HYD", 500);
        graph.addFlight("BLR", "MAA", 350);
        graph.addFlight("BLR", "COK", 530);
        graph.addFlight("HYD", "MAA", 520);
        graph.addFlight("HYD", "CCU", 1000);
        graph.addFlight("MAA", "COK", 550);
        graph.addFlight("CCU", "MAA", 1350);

int choice = 0;

        do {
            System.out.println("\nFLIGHT ROUTE OPTIMIZATION SYSTEM");
            System.out.println("1. Display Airports");
            System.out.println("2. Display Flight Routes");
            System.out.println("3. Find Shortest Route");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    graph.displayAirports();
                    break;

                case 2:
                    graph.displayFlights();
                    break;

                case 3:
                    System.out.print("Enter source airport: ");
                    String source = sc.nextLine().trim();

                    System.out.print("Enter destination airport: ");
                    String destination = sc.nextLine().trim();

                    graph.findShortestRoute(source, destination);
                    break;

                case 4:
                    System.out.println("Thank you for using the system.");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 4);

        sc.close();
    }
}

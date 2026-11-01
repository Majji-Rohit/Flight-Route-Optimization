
import java.util.*;

public class FlightRouteOptimization {

    static class Edge {
        String destination;
        int distance;
        double fare;

        Edge(String destination, int distance, double fare) {
            this.destination = destination;
            this.distance = distance;
            this.fare = fare;
        }
    }

    static class Node {
        String airport;
        double cost;

        Node(String airport, double cost) {
            this.airport = airport;
            this.cost = cost;
        }
    }

    static class Graph {

        private final Map<String, List<Edge>> adjList =
            new LinkedHashMap<>();

        void addAirport(String airport) {
            adjList.putIfAbsent(
                airport.toUpperCase(),
                new ArrayList<>()
            );
        }

        void addFlight(
            String source,
            String destination,
            int distance,
            double fare
        ) {
            source = source.toUpperCase();
            destination = destination.toUpperCase();

            if (distance <= 0 || fare <= 0) {
                throw new IllegalArgumentException(
                    "Distance and fare must be positive."
                );
            }

            if (!adjList.containsKey(source)
                    || !adjList.containsKey(destination)) {
                throw new IllegalArgumentException(
                    "Airport does not exist."
                );
            }

            adjList.get(source).add(
                new Edge(destination, distance, fare)
            );

            adjList.get(destination).add(
                new Edge(source, distance, fare)
            );
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
                            + " | Distance: " + edge.distance
                            + " km | Fare: Rs. "
                            + String.format("%.2f", edge.fare)
                        );
                    }
                }
            }
        }

        void findOptimalRoute(
            String source,
            String destination,
            boolean useFare
        ) {

            source = source.toUpperCase();
            destination = destination.toUpperCase();

            if (!adjList.containsKey(source)
                    || !adjList.containsKey(destination)) {

                System.out.println("Invalid airport code.");
                return;
            }

            Map<String, Double> distances = new HashMap<>();
            Map<String, String> parent = new HashMap<>();

            for (String airport : adjList.keySet()) {
                distances.put(airport, Double.MAX_VALUE);
            }

            PriorityQueue<Node> pq = new PriorityQueue<>(
                Comparator.comparingDouble(node -> node.cost)
            );

            distances.put(source, 0.0);
            pq.offer(new Node(source, 0.0));

            while (!pq.isEmpty()) {

                Node current = pq.poll();
                String airport = current.airport;

                if (current.cost > distances.get(airport)) {
                    continue;
                }

                if (airport.equals(destination)) {
                    break;
                }

                for (Edge edge : adjList.get(airport)) {

                    double weight = useFare
                        ? edge.fare
                        : edge.distance;

                    double newCost = current.cost + weight;

                    if (newCost < distances.get(edge.destination)) {

                        distances.put(edge.destination, newCost);

                        parent.put(edge.destination, airport);

                        pq.offer(
                            new Node(edge.destination, newCost)
                        );
                    }
                }
            }

            if (distances.get(destination) == Double.MAX_VALUE) {

                System.out.println(
                    "No route available between "
                    + source + " and " + destination
                );

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

            if (useFare) {

                System.out.printf(
                    "Cheapest Fare: Rs. %.2f%n",
                    distances.get(destination)
                );

            } else {

                System.out.printf(
                    "Shortest Distance: %.0f km%n",
                    distances.get(destination)
                );
            }

            System.out.print("Route: ");

            for (int i = 0; i < path.size(); i++) {

                System.out.print(path.get(i));

                if (i < path.size() - 1) {
                    System.out.print(" -> ");
                }
            }

            System.out.println(
                "\nNumber of Flights: " + (path.size() - 1)
            );

            System.out.println("================================");
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Graph graph = new Graph();

        System.out.println("Loading data from MySQL...");

        List<String> airports = AirportDAO.getAllAirports();

        List<FlightDAO.Flight> flights =
            FlightDAO.getAllFlights();

        if (airports.isEmpty() || flights.isEmpty()) {

            System.out.println(
                "Unable to load project data from database."
            );

            sc.close();
            return;
        }

        for (String airport : airports) {
            graph.addAirport(airport);
        }

        for (FlightDAO.Flight flight : flights) {

            graph.addFlight(
                flight.source,
                flight.destination,
                flight.distance,
                flight.fare
            );
        }

        System.out.println("Database data loaded successfully!");

        int choice = 0;

        do {

            System.out.println(
                "\nFLIGHT ROUTE OPTIMIZATION SYSTEM"
            );

            System.out.println("1. Display Airports");
            System.out.println("2. Display Flight Routes");
            System.out.println("3. Find Shortest Route");
            System.out.println("4. Find Cheapest Route");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {

                System.out.println(
                    "Please enter a valid number."
                );

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
                    String source1 = sc.nextLine().trim();

                    System.out.print(
                        "Enter destination airport: "
                    );

                    String destination1 = sc.nextLine().trim();

                    graph.findOptimalRoute(
                        source1,
                        destination1,
                        false
                    );

                    break;

                case 4:

                    System.out.print("Enter source airport: ");
                    String source2 = sc.nextLine().trim();

                    System.out.print(
                        "Enter destination airport: "
                    );

                    String destination2 = sc.nextLine().trim();

                    graph.findOptimalRoute(
                        source2,
                        destination2,
                        true
                    );

                    break;

                case 5:

                    System.out.println(
                        "Thank you for using the system."
                    );

                    break;

                default:

                    System.out.println(
                        "Invalid choice. Try again."
                    );
            }

        } while (choice != 5);

        sc.close();
    }
}

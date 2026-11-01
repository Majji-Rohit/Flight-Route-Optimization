
import java.sql.*;
import java.util.*;

public class FlightDAO {

    public static class Flight {
        String source;
        String destination;
        int distance;
        double fare;

        Flight(String source, String destination, int distance, double fare) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
            this.fare = fare;
        }
    }

    public static List<Flight> getAllFlights() {

        List<Flight> flights = new ArrayList<>();

        String query = "SELECT source, destination, distance, fare FROM Flights";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {

                String source = rs.getString("source");
                String destination = rs.getString("destination");
                int distance = rs.getInt("distance");
                double fare = rs.getDouble("fare");

                flights.add(new Flight(source, destination, distance, fare));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving flights: " + e.getMessage());
        }

        return flights;
    }

    public static void main(String[] args) {

        List<Flight> flights = getAllFlights();

        System.out.println("Flight connections retrieved from MySQL:");

        for (Flight flight : flights) {
            System.out.println(
                flight.source + " -> " +
                flight.destination +
                " | Distance: " + flight.distance +
                " km | Fare: Rs. " + flight.fare
            );
        }

        System.out.println("Total connections: " + flights.size());
    }
}


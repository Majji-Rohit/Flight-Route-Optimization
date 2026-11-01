
import java.sql.*;
import java.util.*;

public class AirportDAO {

    public static List<String> getAllAirports() {

        List<String> airports = new ArrayList<>();

        String query = "SELECT airport_code FROM Airports";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                airports.add(rs.getString("airport_code"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving airports: " + e.getMessage());
        }

        return airports;
    }

    public static void main(String[] args) {

        List<String> airports = getAllAirports();

        System.out.println("Airports retrieved from MySQL:");

        for (String airport : airports) {
            System.out.println(airport);
        }
    }
}

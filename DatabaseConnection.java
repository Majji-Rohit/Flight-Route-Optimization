
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/flight_route_db";

    private static final String USER = "flight_app";

    public static Connection getConnection() throws SQLException {

        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isEmpty()) {
            throw new SQLException("Database password is not configured.");
        }

        return DriverManager.getConnection(URL, USER, password);
    }

    public static void main(String[] args) {

        try (Connection con = getConnection()) {

            System.out.println("MySQL connection successful!");
            System.out.println("Connected to: " + con.getCatalog());

        } catch (SQLException e) {

            System.out.println("Database connection failed!");
            System.out.println(e.getMessage());
        }
    }
}

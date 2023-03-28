import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Postgresql {
    public static Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/root";
        String user = "root";
        String password = "root";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}

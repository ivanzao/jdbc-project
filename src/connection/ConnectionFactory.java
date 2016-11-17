package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:mysql://187.72.87.219/C12";
    private static final String USER = "C12";
    private static final String PASSWORD = "c12";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Could not get MySQL connection.\n" + e.getMessage());
            return null;
        }
    }
}

package module.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
    private static Conn instance;
    private final String DB_URL;
    private final String DB_USER;
    private final String DB_PASSWORD;

    private Conn() {
        Dotenv dotenv = Dotenv.load();
        this.DB_URL = dotenv.get("DB_URL");
        this.DB_USER = dotenv.get("DB_USER");
        this.DB_PASSWORD = dotenv.get("DB_PASSWORD");
    }

    public static Conn getInstance() {
        if (instance == null) {
            instance = new Conn();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connection established successfully.");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            throw e;
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            }
        }
    }
}
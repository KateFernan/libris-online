package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static final String URL =
		    System.getenv("DB_URL");

		private static final String USER =
		    System.getenv("DB_USER");

		private static final String PASSWORD =
		    System.getenv("DB_PASSWORD");

    private static Connection conn = null;

    public static Connection getConnection() {

        try {

            if (conn == null || conn.isClosed()) {

                conn = DriverManager.getConnection(URL, USER, PASSWORD);

                System.out.println("Connected to PostgreSQL successfully!");
            }

        } catch (SQLException e) {

            System.out.println("Connection failed.");
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection connect() {
        return getConnection();
    }
}
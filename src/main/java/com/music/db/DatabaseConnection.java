package com.music.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:h2:mem:music_db;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    // Используем in-memory базу данных для демонстрации
    // Для постоянного хранения используйте: jdbc:h2:~/music_db

    static {
        try {
            // Регистрируем драйвер
            Class.forName("org.h2.Driver");
            // Инициализируем базу данных при первой загрузке класса
            DatabaseInitializer.initializeDatabase();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load H2 JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            // Устанавливаем автоматическое подтверждение транзакций
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            System.err.println("Failed to get database connection: " + e.getMessage());
            throw e;
        }
    }

    // Метод для закрытия соединения
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}
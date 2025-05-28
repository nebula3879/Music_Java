package com.music.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Включение режима совместимости с MySQL для корректной работы AUTO_INCREMENT
            statement.execute("SET MODE MySQL");

            // Создание таблицы Users с проверкой на существование
            String createUsersTable = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "email VARCHAR(100) NOT NULL UNIQUE" +
                    ");";

            // Создание таблицы Tracks
            String createTracksTable = "CREATE TABLE IF NOT EXISTS Tracks (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255) NOT NULL, " +
                    "artist VARCHAR(255) NOT NULL" +
                    ");";

            // Создание таблицы Playlists с каскадным удалением
            String createPlaylistsTable = "CREATE TABLE IF NOT EXISTS Playlists (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_id INT NOT NULL, " +
                    "track_id INT NOT NULL, " +
                    "FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (track_id) REFERENCES Tracks(id) ON DELETE CASCADE, " +
                    "UNIQUE (user_id, track_id)" +  // Уникальная пара пользователь-трек
                    ");";

            // Создание индексов для улучшения производительности
            String createIndexes = "CREATE INDEX IF NOT EXISTS idx_playlist_user ON Playlists(user_id); " +
                    "CREATE INDEX IF NOT EXISTS idx_playlist_track ON Playlists(track_id);";

            statement.executeUpdate(createUsersTable);
            statement.executeUpdate(createTracksTable);
            statement.executeUpdate(createPlaylistsTable);
            statement.executeUpdate(createIndexes);

            System.out.println("Database tables initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}
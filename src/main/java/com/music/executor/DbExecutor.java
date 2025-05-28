package com.music.executor;

import com.music.db.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DbExecutor {
    private static DbExecutor instance;

    private DbExecutor() {}

    public static DbExecutor getInstance() {
        if (instance == null) {
            instance = new DbExecutor();
        }
        return instance;
    }

    public <T> List<T> query(String sql, Function<ResultSet, T> extractor, Object... params) {
        List<T> result = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(extractor.apply(rs));
                }
            }
        } catch (SQLException e) {
            handleSQLException("Query error", sql, e);
        }
        return result;
    }

    public int queryForInt(String sql, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            handleSQLException("Query for int error", sql, e);
            return 0;
        }
    }

    public int update(String sql, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setParameters(stmt, params);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            handleSQLException("Update error", sql, e);
            return 0;
        }
    }

    public int updateWithGeneratedKeys(String sql, Object... params) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            setParameters(stmt, params);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            handleSQLException("Update with keys error", sql, e);
            return 0;
        }
    }

    private void setParameters(PreparedStatement stmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
    }

    private void handleSQLException(String message, String sql, SQLException e) {
        System.err.println(message + ": " + e.getMessage());
        System.err.println("SQL: " + sql);
        e.printStackTrace();
    }
}
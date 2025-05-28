package com.music.dao;

import com.music.executor.DbExecutor;
import com.music.extractor.UserExtractor;
import com.music.model.User;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private final DbExecutor dbExecutor;

    private UserDAO() {
        this.dbExecutor = DbExecutor.getInstance();
    }

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }
        return instance;
    }

    public int addUser(String name, String email) {
        String sql = "INSERT INTO Users (name, email) VALUES (?, ?)";
        return dbExecutor.updateWithGeneratedKeys(sql, name, email);
    }

    public List<User> getUsers(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Users ORDER BY id LIMIT ? OFFSET ?";
        return dbExecutor.query(sql, UserExtractor::extract, pageSize, offset);
    }

    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        return dbExecutor.queryForInt(sql);
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        return dbExecutor.update(sql, id) > 0;
    }

    public User getUserById(int id) {
        String sql = "SELECT * FROM Users WHERE id = ?";
        List<User> users = dbExecutor.query(sql, UserExtractor::extract, id);
        return users.isEmpty() ? null : users.get(0);
    }

    public boolean updateUser(int id, String name, String email) {
        String sql = "UPDATE Users SET name = ?, email = ? WHERE id = ?";
        return dbExecutor.update(sql, name, email, id) > 0;
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users";
        return dbExecutor.query(sql, UserExtractor::extract);
    }
}
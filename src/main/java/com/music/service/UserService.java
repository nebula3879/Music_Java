package com.music.service;

import com.music.dao.UserDAO;
import com.music.model.User;
import java.util.List;

public class UserService {
    private static UserService instance;
    private final UserDAO userDAO;

    private UserService() {
        this.userDAO = UserDAO.getInstance();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public int addUser(String name, String email) {
        return userDAO.addUser(name, email);
    }

    public List<User> getUsers(int page, int pageSize) {
        return userDAO.getUsers(page, pageSize);
    }

    public int getTotalUsers() {
        return userDAO.getTotalUsers();
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public boolean updateUser(int id, String name, String email) {
        return userDAO.updateUser(id, name, email);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
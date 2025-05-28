package com.music.servlets;

import com.music.model.User;
import com.music.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            handleEditUser(request, response);
            return;
        }

        int page = parsePageNumber(request);
        int pageSize = parsePageSize(request);
        int totalUsers = userService.getTotalUsers();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        request.setAttribute("users", userService.getUsers(page, pageSize));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAddUser(request);
        } else if ("delete".equals(action)) {
            handleDeleteUser(request);
        } else if ("edit".equals(action)) {
            handleUpdateUser(request);
        }

        response.sendRedirect("users");
    }

    private void handleEditUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userService.getUserById(id);

            if (user == null) {
                response.sendRedirect("users");
                return;
            }

            request.setAttribute("user", user);
            request.setAttribute("action", "edit");

            request.getRequestDispatcher("/editUser.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("users");
        }
    }

    private void handleAddUser(HttpServletRequest request) {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        if (name != null && !name.isEmpty() && email != null && !email.isEmpty()) {
            userService.addUser(name, email);
        }
    }

    private void handleDeleteUser(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                userService.deleteUser(id);
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Неверный ID пользователя");
            }
        }
    }

    private void handleUpdateUser(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            userService.updateUser(id, name, email);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Неверные параметры для обновления пользователя");
        }
    }

    private int parsePageNumber(HttpServletRequest request) {
        try {
            return Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    private int parsePageSize(HttpServletRequest request) {
        try {
            int size = Integer.parseInt(request.getParameter("size"));
            return size > 0 ? size : DEFAULT_PAGE_SIZE;
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }
}
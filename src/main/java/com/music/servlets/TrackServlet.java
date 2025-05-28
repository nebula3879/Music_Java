// TrackServlet.java
package com.music.servlets;

import com.music.model.Track;
import com.music.service.TrackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/tracks")
public class TrackServlet extends HttpServlet {
    private final TrackService trackService = TrackService.getInstance();
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            handleEditTrack(request, response);
            return;
        }

        int page = parsePageNumber(request);
        int pageSize = parsePageSize(request);
        int totalTracks = trackService.getTotalTracks();
        int totalPages = (int) Math.ceil((double) totalTracks / pageSize);

        request.setAttribute("tracks", trackService.getTracks(page, pageSize));
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/tracks.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAddTrack(request);
        } else if ("delete".equals(action)) {
            handleDeleteTrack(request);
        } else if ("edit".equals(action)) {
            handleUpdateTrack(request);
        }

        response.sendRedirect("tracks");
    }

    private void handleEditTrack(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Track track = trackService.getTrackById(id);

            if (track == null) {
                response.sendRedirect("tracks");
                return;
            }

            request.setAttribute("track", track);
            request.setAttribute("action", "edit");

            request.getRequestDispatcher("/editTrack.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("tracks");
        }
    }

    private void handleAddTrack(HttpServletRequest request) {
        String title = request.getParameter("title");
        String artist = request.getParameter("artist");
        if (title != null && artist != null) {
            trackService.addTrack(title, artist);
        }
    }

    private void handleDeleteTrack(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (id != null) {
            try {
                trackService.deleteTrack(Integer.parseInt(id));
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Неверный ID трека");
            }
        }
    }

    private void handleUpdateTrack(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String artist = request.getParameter("artist");
            trackService.updateTrack(id, title, artist);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Неверные параметры для обновления трека");
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
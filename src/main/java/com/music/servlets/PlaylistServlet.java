// PlaylistServlet.java
package com.music.servlets;

import com.music.model.Playlist;
import com.music.model.User;
import com.music.model.Track;
import com.music.service.PlaylistService;
import com.music.service.UserService;
import com.music.service.TrackService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/playlists")
public class PlaylistServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();
    private final PlaylistService playlistService = PlaylistService.getInstance();
    private final TrackService trackService = TrackService.getInstance();
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            handleEditPlaylist(request, response);
            return;
        }

        int page = parsePageNumber(request);
        int pageSize = parsePageSize(request);

        List<Playlist> playlists = playlistService.getPlaylists(page, pageSize);
        int totalPlaylists = playlistService.getTotalPlaylists();
        int totalPages = (int) Math.ceil((double) totalPlaylists / pageSize);

        List<User> users = userService.getAllUsers();
        List<Track> allTracks = trackService.getAllTracks();

        Map<Integer, String> userIdToNameMap = users.stream()
                .collect(Collectors.toMap(User::getId, User::getName));

        Map<Integer, String> trackIdToTitleMap = allTracks.stream()
                .collect(Collectors.toMap(Track::getId,
                        t -> t.getTitle() + " - " + t.getArtist()));

        request.setAttribute("users", users);
        request.setAttribute("playlists", playlists);
        request.setAttribute("allTracks", allTracks);
        request.setAttribute("userIdToNameMap", userIdToNameMap);
        request.setAttribute("trackIdToTitleMap", trackIdToTitleMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/playlists.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAddToPlaylist(request);
        } else if ("delete".equals(action)) {
            handleRemoveFromPlaylist(request);
        } else if ("edit".equals(action)) {
            handleUpdatePlaylist(request);
        }

        response.sendRedirect("playlists");
    }

    private void handleEditPlaylist(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Playlist playlist = playlistService.getPlaylistById(id);

            if (playlist == null) {
                response.sendRedirect("playlists");
                return;
            }

            List<User> users = userService.getAllUsers();
            List<Track> tracks = trackService.getAllTracks();

            request.setAttribute("playlist", playlist);
            request.setAttribute("users", users);
            request.setAttribute("tracks", tracks);
            request.setAttribute("action", "edit");

            request.getRequestDispatcher("/editPlaylist.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect("playlists");
        }
    }

    private void handleAddToPlaylist(HttpServletRequest request) {
        try {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int trackId = Integer.parseInt(request.getParameter("trackId"));
            playlistService.addToPlaylist(userId, trackId);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Неверные параметры для добавления в плейлист");
        }
    }

    private void handleRemoveFromPlaylist(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            playlistService.removeFromPlaylist(id);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Неверный ID для удаления из плейлиста");
        }
    }

    private void handleUpdatePlaylist(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int trackId = Integer.parseInt(request.getParameter("trackId"));
            playlistService.updatePlaylist(id, userId, trackId);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Неверные параметры для обновления плейлиста");
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
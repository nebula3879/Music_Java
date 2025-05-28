package com.music.service;

import com.music.dao.PlaylistDAO;
import com.music.model.Playlist;
import java.util.List;

public class PlaylistService {
    private static PlaylistService instance;
    private final PlaylistDAO playlistDAO;

    private PlaylistService() {
        this.playlistDAO = PlaylistDAO.getInstance();
    }

    public static PlaylistService getInstance() {
        if (instance == null) {
            instance = new PlaylistService();
        }
        return instance;
    }

    public int addToPlaylist(int userId, int trackId) {
        return playlistDAO.addToPlaylist(userId, trackId);
    }

    public List<Playlist> getPlaylists(int page, int pageSize) {
        return playlistDAO.getPlaylists(page, pageSize);
    }

    public int getTotalPlaylists() {
        return playlistDAO.getTotalPlaylists();
    }

    public boolean removeFromPlaylist(int id) {
        return playlistDAO.removeFromPlaylist(id);
    }

    public List<Playlist> getAllPlaylists() {
        return playlistDAO.getAllPlaylists();
    }

    public List<Playlist> getUserPlaylists(int userId) {
        return playlistDAO.getUserPlaylists(userId);
    }

    public Playlist getPlaylistById(int id) {
        return playlistDAO.getPlaylistById(id);
    }

    public boolean updatePlaylist(int id, int userId, int trackId) {
        return playlistDAO.updatePlaylist(id, userId, trackId);
    }
}
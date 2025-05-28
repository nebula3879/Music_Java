package com.music.dao;

import com.music.executor.DbExecutor;
import com.music.extractor.PlaylistExtractor;
import com.music.model.Playlist;
import java.util.List;

public class PlaylistDAO {
    private static PlaylistDAO instance;
    private final DbExecutor dbExecutor;

    private PlaylistDAO() {
        this.dbExecutor = DbExecutor.getInstance();
    }

    public static PlaylistDAO getInstance() {
        if (instance == null) {
            instance = new PlaylistDAO();
        }
        return instance;
    }

    public int addToPlaylist(int userId, int trackId) {
        String sql = "INSERT INTO Playlists (user_id, track_id) VALUES (?, ?)";
        return dbExecutor.updateWithGeneratedKeys(sql, userId, trackId);
    }

    public List<Playlist> getPlaylists(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Playlists ORDER BY id LIMIT ? OFFSET ?";
        return dbExecutor.query(sql, PlaylistExtractor::extract, pageSize, offset);
    }

    public int getTotalPlaylists() {
        String sql = "SELECT COUNT(*) FROM Playlists";
        return dbExecutor.queryForInt(sql);
    }

    public boolean removeFromPlaylist(int id) {
        String sql = "DELETE FROM Playlists WHERE id = ?";
        return dbExecutor.update(sql, id) > 0;
    }

    public List<Playlist> getAllPlaylists() {
        String sql = "SELECT * FROM Playlists";
        return dbExecutor.query(sql, PlaylistExtractor::extract);
    }

    public List<Playlist> getUserPlaylists(int userId) {
        String sql = "SELECT * FROM Playlists WHERE user_id = ?";
        return dbExecutor.query(sql, PlaylistExtractor::extract, userId);
    }

    public Playlist getPlaylistById(int id) {
        String sql = "SELECT * FROM Playlists WHERE id = ?";
        List<Playlist> playlists = dbExecutor.query(sql, PlaylistExtractor::extract, id);
        return playlists.isEmpty() ? null : playlists.get(0);
    }

    public boolean updatePlaylist(int id, int userId, int trackId) {
        String sql = "UPDATE Playlists SET user_id = ?, track_id = ? WHERE id = ?";
        return dbExecutor.update(sql, userId, trackId, id) > 0;
    }
}
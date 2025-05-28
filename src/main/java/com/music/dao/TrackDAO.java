package com.music.dao;

import com.music.executor.DbExecutor;
import com.music.extractor.TrackExtractor;
import com.music.model.Track;
import java.util.List;

public class TrackDAO {
    private static TrackDAO instance;
    private final DbExecutor dbExecutor;

    private TrackDAO() {
        this.dbExecutor = DbExecutor.getInstance();
    }

    public static TrackDAO getInstance() {
        if (instance == null) {
            instance = new TrackDAO();
        }
        return instance;
    }

    public int addTrack(String title, String artist) {
        String sql = "INSERT INTO Tracks (title, artist) VALUES (?, ?)";
        return dbExecutor.updateWithGeneratedKeys(sql, title, artist);
    }

    public List<Track> getAllTracks() {
        String sql = "SELECT * FROM Tracks";
        return dbExecutor.query(sql, TrackExtractor::extract);
    }

    public boolean deleteTrack(int id) {
        String sql = "DELETE FROM Tracks WHERE id = ?";
        return dbExecutor.update(sql, id) > 0;
    }

    public int getTotalTracks() {
        String sql = "SELECT COUNT(*) FROM Tracks";
        return dbExecutor.queryForInt(sql);
    }

    public List<Track> getTracks(int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT * FROM Tracks ORDER BY id LIMIT ? OFFSET ?";
        return dbExecutor.query(sql, TrackExtractor::extract, pageSize, offset);
    }

    public Track getTrackById(int id) {
        String sql = "SELECT * FROM Tracks WHERE id = ?";
        List<Track> tracks = dbExecutor.query(sql, TrackExtractor::extract, id);
        return tracks.isEmpty() ? null : tracks.get(0);
    }

    public boolean updateTrack(int id, String title, String artist) {
        String sql = "UPDATE Tracks SET title = ?, artist = ? WHERE id = ?";
        return dbExecutor.update(sql, title, artist, id) > 0;
    }
}
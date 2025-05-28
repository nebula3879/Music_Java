// TrackService.java
package com.music.service;

import com.music.dao.TrackDAO;
import com.music.model.Track;
import java.util.List;

public class TrackService {
    private static TrackService instance;
    private final TrackDAO trackDAO;

    private TrackService() {
        this.trackDAO = TrackDAO.getInstance();
    }

    public static TrackService getInstance() {
        if (instance == null) {
            instance = new TrackService();
        }
        return instance;
    }

    public int addTrack(String title, String artist) {
        return trackDAO.addTrack(title, artist);
    }

    public List<Track> getAllTracks() {
        return trackDAO.getAllTracks();
    }

    public boolean deleteTrack(int id) {
        return trackDAO.deleteTrack(id);
    }

    public int getTotalTracks() {
        return trackDAO.getTotalTracks();
    }

    public List<Track> getTracks(int page, int pageSize) {
        return trackDAO.getTracks(page, pageSize);
    }

    public Track getTrackById(int id) {
        return trackDAO.getTrackById(id);
    }

    public boolean updateTrack(int id, String title, String artist) {
        return trackDAO.updateTrack(id, title, artist);
    }
}
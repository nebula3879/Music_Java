package com.music.model;

public class Playlist {
    private int id;
    private int userId;
    private int trackId;

    public Playlist(int id, int userId, int trackId) {
        this.id = id;
        this.userId = userId;
        this.trackId = trackId;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getTrackId() { return trackId; }
}
package com.music.extractor;

import com.music.model.Playlist;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlaylistExtractor {
    public static Playlist extract(ResultSet rs) {
        try {
            return new Playlist(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("track_id")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
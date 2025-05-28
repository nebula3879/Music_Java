package com.music.extractor;

import com.music.model.Track;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackExtractor {
    public static Track extract(ResultSet rs) {
        try {
            return new Track(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("artist")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
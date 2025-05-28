package com.music.extractor;

import com.music.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExtractor {
    public static User extract(ResultSet rs) {
        try {
            return new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
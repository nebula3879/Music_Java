package com.music;

import com.music.model.User;
import com.music.model.Track;
import com.music.service.PlaylistService;
import com.music.service.TrackService;
import com.music.service.UserService;

public class Main {
    public static void main(String[] args) {
        UserService userService = UserService.getInstance();
        PlaylistService playlistService = PlaylistService.getInstance();
        TrackService trackService = TrackService.getInstance();

        // Выводим пользователей
        System.out.println("Users:");
        for (User user : userService.getAllUsers()) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }

        // Выводим треки
        System.out.println("\nTracks:");
        for (Track track : trackService.getAllTracks()) {
            System.out.println("ID: " + track.getId() + ", Title: " + track.getTitle() + ", Artist: " + track.getArtist());
        }
    }
}
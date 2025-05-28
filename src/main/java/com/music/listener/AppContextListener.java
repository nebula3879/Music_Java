package com.music.listener;

import com.music.db.DatabaseInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DatabaseInitializer.initializeDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
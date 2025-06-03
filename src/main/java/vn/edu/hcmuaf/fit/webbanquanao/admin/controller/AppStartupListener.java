package vn.edu.hcmuaf.fit.webbanquanao.admin.controller;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.hcmuaf.fit.webbanquanao.admin.service.UserLogsService;

@WebListener
public class AppStartupListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Tắt log vào database khi app khởi động
        UserLogsService.getInstance().setEnableDatabaseLogging(false);
    }
}

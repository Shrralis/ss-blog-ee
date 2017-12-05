package com.shrralis.ssblog.listener;

import com.shrralis.ssblog.config.PostsConfig;
import com.shrralis.ssblog.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();

        sc.setAttribute("ctx", sc.getContextPath());
        sc.setAttribute("max_post_count", PostsConfig.POSTS_PER_PAGE);
        sc.setAttribute("READER_ORDINAL", User.Scope.READER.ordinal());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}

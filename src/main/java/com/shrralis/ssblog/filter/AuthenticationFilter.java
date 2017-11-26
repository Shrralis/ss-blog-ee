package com.shrralis.ssblog.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        logger.trace("Requested resource: %s", uri);

        HttpSession session = req.getSession(false);

        if (session == null && !(uri.endsWith("signIn") || uri.endsWith("signUp"))) {
            logger.trace("Unauthorized access request");
            res.sendRedirect("/signIn");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // here we can close resources
    }
}

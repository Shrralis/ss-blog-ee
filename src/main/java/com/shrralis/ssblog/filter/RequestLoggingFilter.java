package com.shrralis.ssblog.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

@WebFilter("/RequestLoggingFilter")
public class RequestLoggingFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestLoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Enumeration<String> params = req.getParameterNames();

        while (params.hasMoreElements()) {
            String name = params.nextElement();
            String value = request.getParameter(name);

            logger.trace("%s::Request Params::{%s=%s}", req.getRemoteAddr(), name, value);
        }

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            Arrays.stream(cookies)
                    .forEach(c -> logger.trace("%s::Cookie::{%s,%s}", req.getRemoteAddr(), c.getName(), c.getValue()));
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // here we can close resources
    }
}

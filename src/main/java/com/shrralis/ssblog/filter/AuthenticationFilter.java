package com.shrralis.ssblog.filter;

import com.google.gson.*;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
            .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

                @Override
                public Date deserialize(final JsonElement json,
                                        final Type typeOfT,
                                        final JsonDeserializationContext context) throws JsonParseException {
                    try {
                        return df.parse(json.getAsString());
                    } catch (ParseException e) {
                        return null;
                    }
                }
            })
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();

        if (uri.endsWith(".css") || uri.endsWith(".js")) {
            chain.doFilter(request, response);
            return;
        }
        request.setCharacterEncoding("UTF-8");

        IUserDAO dao;

        try {
            dao = UserJdbcDAOImpl.getDao();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
            return;
        }

        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        Cookie cookie = getCookie("user", req);
        User user = null;
        User userFromDao = null;

        if (session != null || cookie != null) {
            try {
                user = gson.fromJson(
                        URLDecoder.decode((session != null ? session.getAttribute("user") : cookie.getValue())
                                + "", "UTF-8"), User.class);

                if (user != null) {
                    userFromDao = dao.getById(user.getId(), true);
                }
            } catch (SQLException e) {
                logger.debug("Exception with recognizing the user!", e);
                return;
            }
        }

        if ((userFromDao == null || User.Scope.BANNED.equals(user.getScope()) ||
                !userFromDao.getPassword().equals(user.getPassword()))
                && !(uri.endsWith("signIn") || uri.endsWith("signUp"))) {
            res.sendRedirect("/signIn");
        } else {
            if (user != null && ((uri.endsWith("setUserScope") && !User.Scope.ADMIN.equals(user.getScope())) ||
                    (uri.endsWith("createPost") && User.Scope.WRITER.ordinal() > user.getScope().ordinal()))) {
                res.sendRedirect("/");
                return;
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // here we can close resources
    }

    private Cookie getCookie(final String name, final HttpServletRequest request) {
        return request.getCookies() == null ? null : Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}

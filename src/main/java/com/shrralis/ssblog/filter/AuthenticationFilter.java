package com.shrralis.ssblog.filter;

import com.google.gson.*;
import com.shrralis.ssblog.dao.UserJdbcDAOImpl;
import com.shrralis.ssblog.dao.interfaces.IUserDAO;
import com.shrralis.ssblog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
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
import java.util.Date;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private static final Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .serializeNulls()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
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

        IUserDAO dao;

        try {
            dao = UserJdbcDAOImpl.getDao();
        } catch (ClassNotFoundException | SQLException e) {
            logger.debug("Exception!", e);
            return;
        }

        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User user = null;
        User u = null;

        try {
            if (session != null) {
                u = gson.fromJson(
                        URLDecoder.decode(session.getAttribute("user") + "", "UTF-8"),
                        User.class
                );

                if (u != null) {
                    user = dao.getById(u.getId(), true);
                }
            }
        } catch (SQLException e) {
            logger.debug("Exception!", e);
            return;
        }

        if ((session == null || user == null || User.Scope.BANNED.equals(user.getScope()) ||
                !user.getPassword().equals(u.getPassword())) && !(uri.endsWith("signIn") || uri.endsWith("signUp"))) {
            res.sendRedirect("/signIn");
        } else {
            if (user != null) {
                if (uri.endsWith("setUserScope") && !User.Scope.ADMIN.equals(user.getScope())) {
                    res.sendRedirect("/");
                    return;
                }

                if (uri.endsWith("createPost") && User.Scope.WRITER.ordinal() > user.getScope().ordinal()) {
                    res.sendRedirect("/");
                    return;
                } else if ((uri.endsWith("setPostUpdater") || uri.endsWith("removePostUpdater") ||
                        uri.endsWith("setPosted")) && User.Scope.WRITER.ordinal() > user.getScope().ordinal()) {
                    res.sendRedirect("/");
                    return;
                }
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // here we can close resources
    }
}

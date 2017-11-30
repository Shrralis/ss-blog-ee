package com.shrralis.ssblog.servlet.base;

import com.google.gson.*;
import com.shrralis.ssblog.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class ServletWithGsonProcessor extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ServletWithGsonProcessor.class);

    private static final Gson GSON = new GsonBuilder()
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

    protected static Gson getGson() {
        return GSON;
    }

    protected Cookie getCookie(final String name, final HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    protected User getCookieUser(HttpServletRequest request) {
        try {
            String user = null;

            if (request.getSession(false) != null) {
                user = URLDecoder.decode(request.getSession(false)
                        .getAttribute("user") + "", "UTF-8");
            }

            user = user == null ? URLDecoder.decode(getCookie("user", request).getValue(), "UTF-8") : user;

            return getGson().fromJson(user, User.class);
        } catch (UnsupportedEncodingException e) {
            logger.debug("Exception with getting cookie user!", e);
            return null;
        }
    }
}

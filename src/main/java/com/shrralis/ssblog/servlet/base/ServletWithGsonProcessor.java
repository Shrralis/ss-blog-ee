package com.shrralis.ssblog.servlet.base;

import com.google.gson.*;

import javax.servlet.http.HttpServlet;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServletWithGsonProcessor extends HttpServlet {
    private Gson gson = new GsonBuilder()
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

    protected Gson getGson() {
        return gson;
    }
}

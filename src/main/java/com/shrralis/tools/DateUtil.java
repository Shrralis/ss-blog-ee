package com.shrralis.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    private final SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static DateUtil dateUtil = new DateUtil();

    private DateUtil() {}

    public static String toMySQLDateTimeString(Date date) {
        return dateUtil.toMySQLDateTimeStringInstance(date);
    }

    public static Date fromMySQLDateTimeString(String date) {
        return dateUtil.fromMySQLDateTimeStringInstance(date);
    }

    private String toMySQLDateTimeStringInstance(Date date) {
        return date == null ? null : simpleDateTimeFormat.format(date);
    }

    private Date fromMySQLDateTimeStringInstance(String date) {
        try {
            return TextUtil.isEmpty(date) ? null : simpleDateTimeFormat.parse(date);
        } catch (ParseException e) {
            logger.debug("Error with parsing date!", e);
            return null;
        }
    }
}

package com.shrralis.tools.model;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.EnumSet;

public class JsonError {
    private int errno = Error.NO_ERROR.getId();
    private String errmsg = Error.NO_ERROR.getMessage();

    public JsonError() {
    }

    public JsonError(Error error) {
        setError(error);
    }

    public JsonError(String message) {
        setMessage(message);
    }

    public void setError(Error e) {
        errno = e.getId();
        errmsg = e.getMessage();
    }

    public void setMessage(String message) {
        errno = -1;
        errmsg = message;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public int getErrno() {
        return errno;
    }

    @Override
    public String toString() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.add("error", new JsonObject());

        jsonObject = (JsonObject) jsonObject.get("error");

        jsonObject.addProperty("errno", errno);
        jsonObject.addProperty("errmsg", errmsg);
        return new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);
    }

    public enum Error {
        NO_ERROR(0, "No error"),
        UNEXPECTED(1, "Unexpected error"),
        DATABASE(2, "Database error"),
        LOGIN_EMPTY(3, "Empty login"),
        PASSWORD_EMPTY(4, "Empty password"),
        USER_NOT_EXISTS(5, "User doesn't exist"),
        PASSWORD_INCORRECT(6, "Incorrect password"),
        USER_ALREADY_EXISTS(7, "User already exists"),
        SIGN_UP_FAIL(8, "Sign up failed"),
        ALREADY_EXISTS(9, "Already exists"),
        POST_NOT_EXISTS(10, "Post doesn't exist"),
        ADD_POST_UPDATER_FAIL(11, "Adding new post updater failed"),
        ADD_POST_FAIL(11, "Adding new post failed"),
        TITLE_EMPTY(12, "Empty title"),
        TITLE_MAX_LENGTH(13, "Maximum title length exceeded (max is 64)"),
        DESCRIPTION_EMPTY(14, "Empty description"),
        DESCRIPTION_MAX_LENGTH(15, "Maximum description length exceeded (max is 128)"),
        TEXT_EMPTY(16, "Empty text"),
        TEXT_MAX_LENGTH(17, "Maximum text length exceeded (max is 2048)"),
        POST_ID_BAD(18, "Bad post ID"),
        NO_ACCESS(19, "No access"),
        UPDATE_DATA_BAD(20, "Bad data for updating"),
        UPDATER_ID_BAD(21, "Bad updater ID"),
        POST_HAVE_NOT_UPDATER(22, "The post doesn't have this updater"),
        USER_ID_BAD(23, "Bad user ID"),
        SCOPE_BAD(24, "Bad user scope"),
        IMAGE_INTERNAL(25, "Internal error with processing the image"),
        IMAGE_BAD_PART(26, "Bad part of the image"),
        IMAGE_WRITING_FAIL(27, "Writing image to the server fail"),
        IMAGE_BAD_ID(28, "Bad ID of the image"),
        IMAGE_NOT_EXISTS(29, "Image doesn't exist"),;

        private static final ArrayList<Error> lookup = new ArrayList<>();

        static {
            for (Error e : EnumSet.allOf(Error.class)) {
                lookup.add(e.getId(), e);
            }
        }

        private final int id;
        private final String message;

        Error(int id, String message) {
            this.id = id;
            this.message = message;
        }

        public static Error get(int id) {
            return lookup.get(id);
        }

        public int getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }
    }
}

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
        EMPTY_LOGIN(3, "Empty login"),
        EMPTY_PASSWORD(4, "Empty password"),
        USER_NOT_EXISTS(5, "User doesn't exist"),
        INCORRECT_PASSWORD(6, "Incorrect password"),
        USER_ALREADY_EXISTS(7, "User already exists"),
        SIGN_UP_FAIL(8, "Sign up failed"),
        ALREADY_EXISTS(9, "Already exists"),
        POST_NOT_EXISTS(10, "Post doesn't exist"),
        ADD_POST_UPDATER_FAIL(11, "Adding new post updater failed"),
        ADD_POST_FAIL(11, "Adding new post failed"),
        EMPTY_TITLE(12, "Empty title"),
        MAX_LENGTH_TITLE(13, "Maximum title length exceeded (max is 64)"),
        EMPTY_DESCRIPTION(14, "Empty description"),
        MAX_LENGTH_DESCRIPTION(15, "Maximum description length exceeded (max is 128)"),
        EMPTY_TEXT(16, "Empty text"),
        MAX_LENGTH_TEXT(17, "Maximum text length exceeded (max is 2048)"),;

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

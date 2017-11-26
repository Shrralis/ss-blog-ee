package com.shrralis.tools.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JsonResponse {
    public static final int OK = 0;
    public static final int ERROR = -1;

    private Integer result;
    private JsonError error;
    private ArrayList<Object> data;

    public JsonResponse(Object data) {
        this.data = new ArrayList<>();

        if (data instanceof Collection) {
            this.data.addAll((Collection) data);
        } else {
            this.data.add(data);
        }

        result = OK;
    }

    public JsonResponse(int result) {
        this.result = result;
    }

    public JsonResponse(JsonError.Error error) {
        this.error = new JsonError(error);
        result = ERROR;
    }

    public Integer getResult() {
        return result;
    }

    public JsonError getError() {
        return error;
    }

    public List<Object> getData() {
        return data;
    }
}

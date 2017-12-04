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
    private Integer count = 0;

    private JsonResponse() {
    }

    public JsonResponse(Object data) {
        setData(data);
    }

    public JsonResponse(int result) {
        setResult(result);
    }

    public JsonResponse(JsonError.Error error) {
        setError(error);
    }

    public Integer getResult() {
        return result;
    }

    private void setResult(int result) {
        this.result = result;
    }

    public JsonError getError() {
        return error;
    }

    private void setError(JsonError.Error error) {
        this.error = new JsonError(error);
        result = ERROR;
    }

    public List<Object> getData() {
        return data;
    }

    private void setData(Object data) {
        this.data = new ArrayList<>();

        if (data instanceof Collection) {
            this.data.addAll((Collection) data);
        } else {
            this.data.add(data);
        }

        count = this.data.size();
        result = OK;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "JsonResponse{" +
                "result=" + result +
                ", error=" + error +
                ", data=" + data +
                ", count=" + count +
                '}';
    }

    public static final class Builder {
        private JsonResponse jsonResponse;

        private Builder() {
            jsonResponse = new JsonResponse();
        }

        public static Builder aJsonResponse() {
            return new Builder();
        }

        public Builder setData(Object data) {
            jsonResponse.setData(data);
            return this;
        }

        public Builder setResult(int result) {
            jsonResponse.setResult(result);
            return this;
        }

        public Builder setError(JsonError.Error error) {
            jsonResponse.setError(error);
            return this;
        }

        public Builder setCount(Integer count) {
            jsonResponse.setCount(count);
            return this;
        }

        public JsonResponse build() {
            return jsonResponse;
        }
    }
}

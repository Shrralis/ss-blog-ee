package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import java.util.HashMap;
import java.util.Map;

public class EditUpdaterDTO {
    private User cookieUser;
    private Map<String, Integer> data = new HashMap<>();

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public Integer getPostId() {
        return data.get("id");
    }

    public void setPostId(Integer id) {
        data.put("id", id);
    }

    public Integer getUpdaterId() {
        return data.get("updater_id");
    }

    public void setUpdaterId(Integer id) {
        data.put("updater_id", id);
    }

    public static class Builder {
        private EditUpdaterDTO dto;

        public Builder() {
            dto = new EditUpdaterDTO();
        }

        public Builder setCookieUser(User user) {
            dto.setCookieUser(user);
            return this;
        }

        public Builder setPostId(Integer id) {
            dto.setPostId(id);
            return this;
        }

        public Builder setUpdaterId(Integer id) {
            dto.setUpdaterId(id);
            return this;
        }

        public EditUpdaterDTO build() {
            return dto;
        }
    }
}

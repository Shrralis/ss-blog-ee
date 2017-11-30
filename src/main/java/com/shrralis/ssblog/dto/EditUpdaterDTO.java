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

    public static final class Builder {
        private EditUpdaterDTO editUpdaterDTO;

        private Builder() {
            editUpdaterDTO = new EditUpdaterDTO();
        }

        public static Builder anEditUpdaterDTO() {
            return new Builder();
        }

        public Builder setCookieUser(User cookieUser) {
            editUpdaterDTO.setCookieUser(cookieUser);
            return this;
        }

        public Builder setPostId(Integer postId) {
            editUpdaterDTO.setPostId(postId);
            return this;
        }

        public Builder setUpdaterId(Integer updaterId) {
            editUpdaterDTO.setUpdaterId(updaterId);
            return this;
        }

        public EditUpdaterDTO build() {
            return editUpdaterDTO;
        }
    }
}

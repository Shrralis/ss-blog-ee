package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import java.util.HashMap;
import java.util.Map;

public class SetPostedDTO {
    private User cookieUser;
    private Map<String, Object> post = new HashMap<>();

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public Integer getPostId() {
        return (Integer) post.get("id");
    }

    public void setPostId(Integer id) {
        post.put("id", id);
    }

    public Boolean isPostPosted() {
        return Boolean.valueOf(post.get("posted").toString());
    }

    public void setPostPosted(Boolean posted) {
        post.put("posted", posted);
    }

    public static class Builder {
        private SetPostedDTO dto;

        public Builder() {
            dto = new SetPostedDTO();
        }

        public Builder setCookieUser(User user) {
            dto.setCookieUser(user);
            return this;
        }

        public Builder setPostId(Integer id) {
            dto.setPostId(id);
            return this;
        }

        public Builder setPostPosted(Boolean posted) {
            dto.setPostPosted(posted);
            return this;
        }

        public SetPostedDTO build() {
            return dto;
        }
    }
}

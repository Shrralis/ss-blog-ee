package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import java.util.HashMap;
import java.util.Map;

public class NewPostDTO {
    private User cookieUser;
    private Map<String, String> post = new HashMap<>();

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public String getPostTitle() {
        return post.get("title");
    }

    public void setPostTitle(String title) {
        post.put("title", title);
    }

    public String getPostDescription() {
        return post.get("description");
    }

    public void setPostDescription(String description) {
        post.put("description", description);
    }

    public String getPostText() {
        return post.get("text");
    }

    public void setPostText(String text) {
        post.put("text", text);
    }

    public static class Builder {
        private NewPostDTO dto;

        public Builder() {
            dto = new NewPostDTO();
        }

        public Builder setCookieUser(User cookieUser) {
            dto.setCookieUser(cookieUser);
            return this;
        }

        public Builder setPostTitle(String title) {
            dto.setPostTitle(title);
            return this;
        }

        public Builder setPostDescription(String description) {
            dto.setPostDescription(description);
            return this;
        }

        public Builder setPostText(String text) {
            dto.setPostText(text);
            return this;
        }

        public NewPostDTO build() {
            return dto;
        }
    }
}

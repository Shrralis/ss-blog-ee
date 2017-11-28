package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import java.util.HashMap;
import java.util.Map;

public class NewEditPostDTO {
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

    public String getPostTitle() {
        return String.valueOf(post.get("title"));
    }

    public void setPostTitle(String title) {
        post.put("title", title);
    }

    public String getPostDescription() {
        return String.valueOf(post.get("description"));
    }

    public void setPostDescription(String description) {
        post.put("description", description);
    }

    public String getPostText() {
        return String.valueOf(post.get("text"));
    }

    public void setPostText(String text) {
        post.put("text", text);
    }

    public Boolean isPosted() {
        return post.get("posted").equals("true");
    }

    public void setPosted(Boolean posted) {
        post.put("posted", posted);
    }

    public static class Builder {
        private NewEditPostDTO dto;

        public Builder() {
            dto = new NewEditPostDTO();
        }

        public Builder setCookieUser(User user) {
            dto.setCookieUser(user);
            return this;
        }

        public Builder setPostId(Integer id) {
            dto.setPostId(id);
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

        public Builder setPosted(boolean posted) {
            dto.setPosted(posted);
            return this;
        }

        public NewEditPostDTO build() {
            return dto;
        }
    }
}

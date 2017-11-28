package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class DeletePostDTO {
    private User cookieUser;
    private Integer postId;

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public static class Builder {
        private DeletePostDTO dto;

        public Builder() {
            dto = new DeletePostDTO();
        }

        public Builder setCookieUser(User user) {
            dto.setCookieUser(user);
            return this;
        }

        public Builder setPostId(Integer id) {
            dto.setPostId(id);
            return this;
        }

        public DeletePostDTO build() {
            return dto;
        }
    }
}

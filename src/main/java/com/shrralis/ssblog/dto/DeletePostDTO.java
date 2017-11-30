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

    public static final class Builder {
        private DeletePostDTO deletePostDTO;

        private Builder() {
            deletePostDTO = new DeletePostDTO();
        }

        public static Builder aDeletePostDTO() {
            return new Builder();
        }

        public Builder setCookieUser(User cookieUser) {
            deletePostDTO.setCookieUser(cookieUser);
            return this;
        }

        public Builder setPostId(Integer postId) {
            deletePostDTO.setPostId(postId);
            return this;
        }

        public DeletePostDTO build() {
            return deletePostDTO;
        }
    }
}

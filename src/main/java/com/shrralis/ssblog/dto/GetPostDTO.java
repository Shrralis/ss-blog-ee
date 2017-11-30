package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class GetPostDTO {
    private Integer postId;
    private Integer userId;
    private User cookieUser;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public static final class Builder {
        private GetPostDTO getPostDTO;

        private Builder() {
            getPostDTO = new GetPostDTO();
        }

        public static Builder aGetPostDTO() {
            return new Builder();
        }

        public Builder setPostId(Integer postId) {
            getPostDTO.setPostId(postId);
            return this;
        }

        public Builder setUserId(Integer userId) {
            getPostDTO.setUserId(userId);
            return this;
        }

        public Builder setCookieUser(User cookieUser) {
            getPostDTO.setCookieUser(cookieUser);
            return this;
        }

        public GetPostDTO build() {
            return getPostDTO;
        }
    }
}

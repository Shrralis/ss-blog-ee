package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class GetPostDTO {
    private Integer postId;
    private Integer userId;
    private Integer count = 20;
    private Integer offset = 0;
    private String substring;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getSubstring() {
        return substring;
    }

    public void setSubstring(String substring) {
        this.substring = substring;
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

        public Builder setCount(Integer count) {
            getPostDTO.setCount(count);
            return this;
        }

        public Builder setOffset(Integer offset) {
            getPostDTO.setOffset(offset);
            return this;
        }

        public Builder setSubstring(String substring) {
            getPostDTO.setSubstring(substring);
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

package com.shrralis.ssblog.dto;

public class PostUpdaterDTO {
    private Integer userId;
    private String userLogin;
    private Boolean isPostUpdater;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public Boolean isPostUpdater() {
        return isPostUpdater;
    }

    public void setPostUpdater(Boolean postUpdater) {
        isPostUpdater = postUpdater;
    }

    public static final class Builder {
        private PostUpdaterDTO postUpdaterDTO;

        private Builder() {
            postUpdaterDTO = new PostUpdaterDTO();
        }

        public static Builder aPostUpdaterDTO() {
            return new Builder();
        }

        public Builder setUserId(Integer userId) {
            postUpdaterDTO.setUserId(userId);
            return this;
        }

        public Builder setUserLogin(String userLogin) {
            postUpdaterDTO.setUserLogin(userLogin);
            return this;
        }

        public Builder setPostUpdater(Boolean isPostUpdater) {
            postUpdaterDTO.setPostUpdater(isPostUpdater);
            return this;
        }

        public PostUpdaterDTO build() {
            return postUpdaterDTO;
        }
    }
}

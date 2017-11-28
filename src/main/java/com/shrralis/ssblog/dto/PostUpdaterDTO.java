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

    public static class Builder {
        private PostUpdaterDTO dto;

        public Builder() {
            dto = new PostUpdaterDTO();
        }

        public Builder setUserId(Integer userId) {
            dto.setUserId(userId);
            return this;
        }

        public Builder setUserLogin(String userLogin) {
            dto.setUserLogin(userLogin);
            return this;
        }

        public Builder setPostUpdater(Boolean postUpdater) {
            dto.setPostUpdater(postUpdater);
            return this;
        }

        public PostUpdaterDTO build() {
            return dto;
        }
    }
}

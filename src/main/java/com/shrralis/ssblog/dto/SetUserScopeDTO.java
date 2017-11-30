package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class SetUserScopeDTO {
    private Integer userId;
    private User.Scope userScope;
    private User cookieUser;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User.Scope getUserScope() {
        return userScope;
    }

    public void setUserScope(User.Scope userScope) {
        this.userScope = userScope;
    }

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public static final class Builder {
        private SetUserScopeDTO setUserScopeDTO;

        private Builder() {
            setUserScopeDTO = new SetUserScopeDTO();
        }

        public static Builder aSetUserScopeDTO() {
            return new Builder();
        }

        public Builder setUserId(Integer userId) {
            setUserScopeDTO.setUserId(userId);
            return this;
        }

        public Builder setUserScope(User.Scope userScope) {
            setUserScopeDTO.setUserScope(userScope);
            return this;
        }

        public Builder setCookieUser(User cookieUser) {
            setUserScopeDTO.setCookieUser(cookieUser);
            return this;
        }

        public SetUserScopeDTO build() {
            return setUserScopeDTO;
        }
    }
}

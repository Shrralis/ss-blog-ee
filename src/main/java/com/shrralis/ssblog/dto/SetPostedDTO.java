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

    public static final class Builder {
        private SetPostedDTO setPostedDTO;

        private Builder() {
            setPostedDTO = new SetPostedDTO();
        }

        public static Builder aSetPostedDTO() {
            return new Builder();
        }

        public Builder setCookieUser(User cookieUser) {
            setPostedDTO.setCookieUser(cookieUser);
            return this;
        }

        public Builder setPostId(Integer id) {
            setPostedDTO.setPostId(id);
            return this;
        }

        public Builder setPostPosted(Boolean posted) {
            setPostedDTO.setPostPosted(posted);
            return this;
        }

        public SetPostedDTO build() {
            return setPostedDTO;
        }
    }
}

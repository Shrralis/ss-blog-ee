package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class GetImageDTO {
    private Integer imageId;
    private User cookieUser;
    private String directoryPath;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public static final class Builder {
        private GetImageDTO getImageDTO;

        private Builder() {
            getImageDTO = new GetImageDTO();
        }

        public static Builder aGetImageDTO() {
            return new Builder();
        }

        public Builder setImageId(Integer imageId) {
            getImageDTO.setImageId(imageId);
            return this;
        }

        public Builder setCookieUser(User requester) {
            getImageDTO.setCookieUser(requester);
            return this;
        }

        public Builder setDirectoryPath(String directoryPath) {
            getImageDTO.setDirectoryPath(directoryPath);
            return this;
        }

        public GetImageDTO build() {
            return getImageDTO;
        }
    }
}

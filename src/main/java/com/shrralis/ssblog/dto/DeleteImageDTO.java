package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

public class DeleteImageDTO {
    private Integer imageId;
    private String directoryPath;
    private User cookieUser;

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public User getCookieUser() {
        return cookieUser;
    }

    public void setCookieUser(User cookieUser) {
        this.cookieUser = cookieUser;
    }

    public static final class Builder {
        private DeleteImageDTO deleteImageDTO;

        private Builder() {
            deleteImageDTO = new DeleteImageDTO();
        }

        public static Builder aDeleteImageDTO() {
            return new Builder();
        }

        public Builder setImageId(Integer imageId) {
            deleteImageDTO.setImageId(imageId);
            return this;
        }

        public Builder setDirectoryPath(String directoryPath) {
            deleteImageDTO.setDirectoryPath(directoryPath);
            return this;
        }

        public Builder setCookieUser(User cookieUser) {
            deleteImageDTO.setCookieUser(cookieUser);
            return this;
        }

        public DeleteImageDTO build() {
            return deleteImageDTO;
        }
    }
}

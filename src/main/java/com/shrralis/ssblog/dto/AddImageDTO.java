package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import javax.servlet.http.Part;

public class AddImageDTO {
    private Part imagePart;
    private String directoryPath;
    private User cookieUser;

    public Part getImagePart() {
        return imagePart;
    }

    public void setImagePart(Part imagePart) {
        this.imagePart = imagePart;
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
        private AddImageDTO addImageDTO;

        private Builder() {
            addImageDTO = new AddImageDTO();
        }

        public static Builder anAddImageDTO() {
            return new Builder();
        }

        public Builder setImagePart(Part imagePart) {
            addImageDTO.setImagePart(imagePart);
            return this;
        }

        public Builder setDirectoryPath(String directoryPath) {
            addImageDTO.setDirectoryPath(directoryPath);
            return this;
        }

        public Builder setCookieUser(User cookieUser) {
            addImageDTO.setCookieUser(cookieUser);
            return this;
        }

        public AddImageDTO build() {
            return addImageDTO;
        }
    }
}

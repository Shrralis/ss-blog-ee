package com.shrralis.ssblog.dto;

import com.shrralis.ssblog.entity.User;

import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class NewEditPostDTO {
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

    public String getPostTitle() {
        return String.valueOf(post.get("title"));
    }

    public void setPostTitle(String title) {
        post.put("title", title);
    }

    public String getPostDescription() {
        return String.valueOf(post.get("description"));
    }

    public void setPostDescription(String description) {
        post.put("description", description);
    }

    public String getPostText() {
        return String.valueOf(post.get("text"));
    }

    public void setPostText(String text) {
        post.put("text", text);
    }

    public Boolean isPosted() {
        return post.get("posted").equals("true");
    }

    public void setPosted(Boolean posted) {
        post.put("posted", posted);
    }

    public String getDirectoryPath() {
        return String.valueOf(post.get("image_path"));
    }

    public void setDirectoryPath(String path) {
        post.put("image_path", path);
    }

    public Part getImagePart() {
        return (Part) post.get("image_part");
    }

    public void setImagePart(Part part) {
        post.put("image_part", part);
    }

    public static final class Builder {
        private NewEditPostDTO newEditPostDTO;

        private Builder() {
            newEditPostDTO = new NewEditPostDTO();
        }

        public static Builder aNewEditPostDTO() {
            return new Builder();
        }

        public Builder setCookieUser(User cookieUser) {
            newEditPostDTO.setCookieUser(cookieUser);
            return this;
        }

        public Builder setPostId(Integer id) {
            newEditPostDTO.setPostId(id);
            return this;
        }

        public Builder setPostTitle(String title) {
            newEditPostDTO.setPostTitle(title);
            return this;
        }

        public Builder setPostDescription(String description) {
            newEditPostDTO.setPostDescription(description);
            return this;
        }

        public Builder setPostText(String text) {
            newEditPostDTO.setPostText(text);
            return this;
        }

        public Builder setPosted(boolean posted) {
            newEditPostDTO.setPosted(posted);
            return this;
        }

        public Builder setDirectoryPath(String path) {
            newEditPostDTO.setDirectoryPath(path);
            return this;
        }

        public Builder setImagePart(Part part) {
            newEditPostDTO.setImagePart(part);
            return this;
        }

        public NewEditPostDTO build() {
            return newEditPostDTO;
        }
    }
}

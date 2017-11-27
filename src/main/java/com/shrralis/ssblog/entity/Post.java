package com.shrralis.ssblog.entity;

import java.time.LocalDateTime;

public class Post {
    private Integer id;
    private String title;
    private String description = "";
    private String text = "";
    private Boolean isPosted = false;
    private User creator;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }

    public Boolean isPosted() {
        return isPosted;
    }

    public void setPosted(Boolean posted) {
        isPosted = posted;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", text='" + text + '\'' +
                ", isPosted=" + isPosted +
                ", creator=" + creator +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public static final class Builder {
        private Post post = new Post();

        public Builder setId(Integer id) {
            post.setId(id);
            return this;
        }

        public Builder setTitle(String title) {
            post.setTitle(title);
            return this;
        }

        public Builder setDescription(String description) {
            post.setDescription(description);
            return this;
        }

        public Builder setText(String text) {
            post.setText(text);
            return this;
        }

        public Builder setPosted(Boolean isPosted) {
            post.setPosted(isPosted);
            return this;
        }

        public Builder setCreator(User creator) {
            post.setCreator(creator);
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            post.setCreatedAt(createdAt);
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            post.setUpdatedAt(updatedAt);
            return this;
        }

        public Post build() {
            return post;
        }
    }
}

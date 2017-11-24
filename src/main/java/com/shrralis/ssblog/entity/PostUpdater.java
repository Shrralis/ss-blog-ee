package com.shrralis.ssblog.entity;

public class PostUpdater {
    private User user;
    private Post post;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public static final class Builder {
        private PostUpdater postUpdater = new PostUpdater();

        public Builder setUser(User user) {
            postUpdater.setUser(user);
            return this;
        }

        public Builder setPost(Post post) {
            postUpdater.setPost(post);
            return this;
        }

        public PostUpdater build() {
            return postUpdater;
        }
    }
}

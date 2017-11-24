package com.shrralis.ssblog.entity;

public class User {
    private Integer id;
    private String login;
    private String password;
    private Scope scope;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public enum Scope {
        READER,
        WRITER,
        ADMIN,
        BANNED,
    }

    public static final class Builder {
        private User user = new User();

        public Builder setId(Integer id) {
            user.setId(id);
            return this;
        }

        public Builder setLogin(String login) {
            user.setLogin(login);
            return this;
        }

        public Builder setPassword(String password) {
            user.setPassword(password);
            return this;
        }

        public Builder setScope(Scope scope) {
            user.setScope(scope);
            return this;
        }

        public User build() {
            return user;
        }
    }
}

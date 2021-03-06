package com.shrralis.ssblog.entity;

public class User {
    private Integer id;
    private String login;
    private String password;
    private Scope scope = Scope.READER;

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", pass='" + password + '\'' +
                ", scope=" + scope +
                '}';
    }

    public enum Scope {
        READER,
        WRITER,
        ADMIN,
        BANNED;

        public static Scope get(String name) {
            Scope result = null;

            for (Scope s : Scope.values()) {
                if (s.name().equalsIgnoreCase(name == null ? null : name.trim())) {
                    result = s;

                    break;
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static final class Builder {
        private User user;

        private Builder() {
            user = new User();
        }

        public static Builder anUser() {
            return new Builder();
        }

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

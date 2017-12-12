package com.shrralis.ssblog.entity;

import org.apache.ibatis.type.Alias;

@Alias("image")
public class Image {
    private Integer id;
    private String src;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", src='" + src + '\'' +
                '}';
    }

    public static final class Builder {
        private Image image;

        private Builder() {
            image = new Image();
        }

        public static Builder anImage() {
            return new Builder();
        }

        public Builder setId(Integer id) {
            image.setId(id);
            return this;
        }

        public Builder setSrc(String src) {
            image.setSrc(src);
            return this;
        }

        public Image build() {
            return image;
        }
    }
}

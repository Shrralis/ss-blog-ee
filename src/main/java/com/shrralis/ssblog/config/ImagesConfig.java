package com.shrralis.ssblog.config;

public class ImagesConfig {
    /**
     * It will be something like '...tomcat/webapps/' +
     * @see com.shrralis.ssblog.config.ImagesConfig#IMAGES_ROOT_PATH
     */
    public static final String IMAGES_ROOT_PATH = "/uploaded_images";
    public static final long IMAGES_MAX_SIZE = 3145728L;                            // it is 3 MB (1024 * 1024 * 3)
}

package com.mijack.sbbs.vo;

/**
 * @author Mr.Yuan
 * @since 2017/10/12
 */

public enum MediaType {
    markdown("markdown", "text/markdown", ".md"),
    jpg("image", "images/jpg", ".jpg");
    private String name;
    private String contentType;
    private String extensionName;


    MediaType(String name, String contentType, String extensionName) {
        this.name = name;
        this.contentType = contentType;
        this.extensionName = extensionName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public static MediaType from(String contentType) {
        for (MediaType mediaType : values()) {
            if (mediaType.contentType.equals(contentType)) {
                return mediaType;
            }
        }
        return null;
    }
}
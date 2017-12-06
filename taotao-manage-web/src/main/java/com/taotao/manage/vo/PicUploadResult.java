package com.taotao.manage.vo;

import java.io.Serializable;

public class PicUploadResult implements Serializable {

    private int error;//值只有为0的时候说明上传图片上传，除此以外说明上传失败

    private String url;//图片地址

    private String width;

    private String height;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}

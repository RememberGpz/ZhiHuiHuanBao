package com.lee.zhihuihuanbao.utils;

/**
 * Created by hjx on 0019 6-19.
 * You can make it better
 */

public class TakePicEvent {
    private String imgPath;

    public TakePicEvent(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

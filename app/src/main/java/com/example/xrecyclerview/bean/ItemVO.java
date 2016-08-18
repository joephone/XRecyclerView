package com.example.xrecyclerview.bean;

import java.io.Serializable;

/**
 * Created by jiangjieqiang on 16/1/18.
 */
public class ItemVO implements Serializable{

    public String desc;
    public int image;

    public ItemVO(String desc, int image) {
        this.desc = desc;
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

package com.gps.camera.timestamp.photo.geotag.map.data.models;

public class LanguageModel {
    int img;
    int name;
    String key;

    public LanguageModel(int img, int name, String key) {
        this.img = img;
        this.name = name;
        this.key = key;
    }

    public int getImg() {
        return img;
    }


    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

}

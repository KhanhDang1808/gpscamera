package com.gps.camera.timestamp.photo.geotag.map.data.models;

public class dataTransfer {
    public int enable;

    public int f154id = 0;
    public String link;
    public String transfer_text;
    public String transfer_title;

    public int getId() {
        return this.f154id;
    }

    public void setId(int i) {
        this.f154id = i;
    }

    public dataTransfer(int i, String str, String str2, String str3) {
        this.enable = i;
        this.transfer_title = str;
        this.transfer_text = str2;
        this.link = str3;
    }
}

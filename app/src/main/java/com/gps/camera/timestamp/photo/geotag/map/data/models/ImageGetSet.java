package com.gps.camera.timestamp.photo.geotag.map.data.models;

import java.util.Date;

public class ImageGetSet implements Comparable<ImageGetSet> {
    private Date dateTime;
    private String img_path;

    public String getImg_path() {
        return this.img_path;
    }

    public void setImg_path(String str) {
        this.img_path = str;
    }

    public Date getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(Date date) {
        this.dateTime = date;
    }

    public int compareTo(ImageGetSet imageGetSet) {
        return getDateTime().compareTo(imageGetSet.getDateTime());
    }
}

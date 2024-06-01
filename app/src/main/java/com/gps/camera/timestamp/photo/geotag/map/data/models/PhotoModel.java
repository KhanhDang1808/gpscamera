package com.gps.camera.timestamp.photo.geotag.map.data.models;

public class PhotoModel {

    private final int idImage;
    private final int title;
    private int content;
    private int position;

    public PhotoModel(int idImage, int title, int content,int pos) {
        this.idImage = idImage;
        this.title = title;
        this.position = pos;
        this.content = content;
    }

    public int getIdImage() {
        return idImage;
    }


    public int getTitle() {
        return title;
    }


    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

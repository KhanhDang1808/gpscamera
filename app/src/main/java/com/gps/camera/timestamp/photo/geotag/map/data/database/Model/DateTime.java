package com.gps.camera.timestamp.photo.geotag.map.data.database.Model;

public class DateTime {
    int date_custom;
    String date_format;
    String date_format_;
    int date_id;
    int date_pos;
    int date_type;
    int isSelect;
    boolean isripple;

    public int getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(int i) {
        this.isSelect = i;
    }

    public void setDate_pos(int i) {
        this.date_pos = i;
    }

    public int getDate_id() {
        return this.date_id;
    }

    public void setDate_id(int i) {
        this.date_id = i;
    }

    public String getDate_format() {
        return this.date_format;
    }

    public void setDate_format(String str) {
        this.date_format = str;
    }

    public String getDate_format_() {
        return this.date_format_;
    }

    public void setDate_format_(String str) {
        this.date_format_ = str;
    }

    public void setDate_type(int i) {
        this.date_type = i;
    }

    public int getDate_custom() {
        return this.date_custom;
    }

    public void setDate_custom(int i) {
        this.date_custom = i;
    }
}

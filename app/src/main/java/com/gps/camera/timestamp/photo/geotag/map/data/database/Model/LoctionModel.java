package com.gps.camera.timestamp.photo.geotag.map.data.database.Model;

public class LoctionModel {

    
    int f174Id;
    String city;
    String country;
    String latitude;
    String loc_line_1;
    String longitude;
    int selection;
    String state;
    String title;

    public int getSelection() {
        return this.selection;
    }

    public void setSelection(int i) {
        this.selection = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public int getId() {
        return this.f174Id;
    }

    public void setId(int i) {
        this.f174Id = i;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String str) {
        this.latitude = str;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String str) {
        this.longitude = str;
    }

    public String getLoc_line_1() {
        return this.loc_line_1;
    }

    public void setLoc_line_1(String str) {
        this.loc_line_1 = str;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String str) {
        this.city = str;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String str) {
        this.state = str;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String str) {
        this.country = str;
    }
}

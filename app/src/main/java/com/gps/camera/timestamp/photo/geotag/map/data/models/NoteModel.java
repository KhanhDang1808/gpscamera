package com.gps.camera.timestamp.photo.geotag.map.data.models;

public class NoteModel {
    private int N_id;
    private String Note;
    private int selected;

    public NoteModel(int i, String str) {
        this.N_id = i;
        this.Note = str;
    }

    public int getN_id() {
        return this.N_id;
    }

    public String getNote() {
        return this.Note;
    }

    public void setNote(String str) {
        this.Note = str;
    }
}

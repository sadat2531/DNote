package com.dnote;

public class Note_type {

    int id;
    String Title, Note;

    // method for Note_type
    public Note_type(int id, String title, String note) {
        this.id = id;
        this.Title = title;
        this.Note = note;
    }

    public Note_type(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

}



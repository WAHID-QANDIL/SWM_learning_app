package com.example.sqlliteproject.models.Tables;

public class Notes {
    private long noteId;
    private String noteTitle;
    private String noteDescription;
    private String noteDate;
    private long user_id;
    private String user_email;

    public Notes(long noteId, String noteTitle, String noteDescription, String noteDate, long user_id, String user_email) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
        this.user_id = user_id;
        this.user_email = user_email;
    }

    public Notes(String noteTitle, String noteDescription, String noteDate, long user_id, String user_email) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
        this.user_id = user_id;
        this.user_email = user_email;
    }
    public Notes(String noteTitle, String noteDescription, long user_id, String user_email) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.user_id = user_id;
        this.user_email = user_email;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}

package com.seakleang.dictionary.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "dictionary")
public class Dictionary {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String detail;
    private boolean bookmark;

    public Dictionary(int id, String word, String detail, boolean bookmark) {
        this.id = id;
        this.word = word;
        this.detail = detail;
        this.bookmark = bookmark;
    }
    @Ignore
    public Dictionary() {
    }
    @Ignore
    public Dictionary(String word, String detail) {
        this.word = word;
        this.detail = detail;
        this.bookmark = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }
}

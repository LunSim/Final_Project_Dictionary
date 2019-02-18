package com.seakleang.dictionary.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "bookmark",
    foreignKeys = @ForeignKey(entity = Dictionary.class,
            parentColumns = "id",
            childColumns = "word_id"),
    indices = {@Index("word_id")})
public class Bookmark {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "word_id")
    public int wordID;

    public Bookmark(int wordID) {
        this.wordID = wordID;
    }
}

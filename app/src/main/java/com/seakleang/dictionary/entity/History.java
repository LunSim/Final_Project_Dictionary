package com.seakleang.dictionary.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "history",
    foreignKeys = @ForeignKey(entity = Dictionary.class,
        parentColumns = "id",
        childColumns = "word_id"),
    indices = {@Index("word_id")})
public class History {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "word_id")
    public int wordID;

    public String date;
    public String time;

    public History(int wordID, String date, String time) {
        this.wordID = wordID;
        this.date = date;
        this.time = time;
    }
}

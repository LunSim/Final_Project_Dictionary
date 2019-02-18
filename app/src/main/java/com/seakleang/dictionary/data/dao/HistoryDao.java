package com.seakleang.dictionary.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.seakleang.dictionary.entity.History;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void add(History history);

    @Query("select date from history where id = :id")
    String getDateById(int id);

    @Query("select time from history where id = :id")
    String getTimeById(int id);

    @Query("select word from dictionary inner join history on dictionary.id = history.word_id order by history.id desc")
    List<String> getWord();

    @Query("delete from history")
    void deleteAll();

    @Query("select id from history order by id desc limit 1")
    int getLastId();
}

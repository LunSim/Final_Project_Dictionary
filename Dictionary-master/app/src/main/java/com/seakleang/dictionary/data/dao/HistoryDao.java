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

    @Update
    void update(History history);

    @Delete
    void delete(History history);

    @Query("select word_id from history where id = :id")
    int getWordIdById(int id);

    @Query("select word_id from history order by id asc")
    List<Integer> getWordId();

}

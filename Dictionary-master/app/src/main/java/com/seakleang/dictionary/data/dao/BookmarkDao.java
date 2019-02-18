package com.seakleang.dictionary.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.seakleang.dictionary.entity.Bookmark;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert
    void add(Bookmark bookmark);

    @Update
    void update(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);

    @Query("delete from bookmark")
    void deleteAll();

    @Query("select word from dictionary inner join bookmark on dictionary.id = bookmark.word_id")
    List<String> getListWord();

    @Query("select word_id from bookmark where id = :id")
    int getWordIdById(int id);

    @Query("select word_id from bookmark order by id asc")
    List<Integer> getWordId();

}

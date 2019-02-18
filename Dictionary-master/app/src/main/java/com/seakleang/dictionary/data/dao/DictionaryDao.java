package com.seakleang.dictionary.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.seakleang.dictionary.entity.Dictionary;

import java.util.List;

@Dao
public interface DictionaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void add(List<Dictionary> dictionaries);

    @Update
    void update(Dictionary... dictionaries);

    @Delete
    void delete(Dictionary... dictionaries);

    @Query("select word from dictionary order by id asc")
    List<String> getWord();

    @Query("select word from dictionary where id = :id")
    String getWordById(int id);

    @Query("select detail from dictionary where id = :id")
    String getDetailById(int id);

    @Query("select id from dictionary where word like :word")
    int getIdByWord(String word);

    @Query("select bookmark from dictionary where id = :id")
    boolean getBookmarkById(int id);

    @Query("update dictionary set bookmark = :bookmark where id = :id")
    void updateBookmarkById(int id, boolean bookmark);

    @Query("select count(*) from dictionary")
    int getNumberOfRow();

    @Query("SELECT * FROM dictionary ORDER BY id ASC")
    List<Dictionary> getDictionarys();

}

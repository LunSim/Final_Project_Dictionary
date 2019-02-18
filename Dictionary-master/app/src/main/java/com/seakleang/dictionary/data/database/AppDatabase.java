package com.seakleang.dictionary.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.seakleang.dictionary.data.dao.BookmarkDao;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.dao.HistoryDao;
import com.seakleang.dictionary.entity.Bookmark;
import com.seakleang.dictionary.entity.Dictionary;
import com.seakleang.dictionary.entity.History;

@Database(version = 1, entities = {Dictionary.class, Bookmark.class, History.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "dictionary_database";
//    private static AppDatabase INSTANCE;

    public abstract DictionaryDao dictionaryDao();
    public abstract BookmarkDao bookmarkDao();
    public abstract HistoryDao historyDao();

    public static AppDatabase getInMemoryDatabase(Context context){
//        if (INSTANCE == null){
//            INSTANCE = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
//        }
//        return INSTANCE;
        return Room.inMemoryDatabaseBuilder(context, AppDatabase.class).allowMainThreadQueries().build();
    }

    public static AppDatabase getFileDatabase(Context context){
//        if (INSTANCE == null){
//            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
//        }
//        return INSTANCE;
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).allowMainThreadQueries().build();
    }

    //public static void destroyInstance(){INSTANCE = null;}

}

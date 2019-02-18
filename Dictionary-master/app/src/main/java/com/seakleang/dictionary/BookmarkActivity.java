package com.seakleang.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.seakleang.dictionary.adapter.BookmarkAndHistoryAdapter;
import com.seakleang.dictionary.data.dao.BookmarkDao;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;

public class BookmarkActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private BookmarkDao bookmarkDao;
    private DictionaryDao dictionaryDao;
    private ListView listView;
    private ImageView ivDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bookmark");

        appDatabase = AppDatabase.getFileDatabase(this);
        bookmarkDao = appDatabase.bookmarkDao();
        dictionaryDao = appDatabase.dictionaryDao();
        listView = findViewById(R.id.listViewBookmark);

        BookmarkAndHistoryAdapter adapter = new BookmarkAndHistoryAdapter(bookmarkDao.getListWord());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = view.findViewById(R.id.tvBookmarkAndHistory);

            int wordId = dictionaryDao.getIdByWord(textView.getText().toString());
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", wordId);
            intent.putExtra("activity", "BookmarkActivity");
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuClear){
            bookmarkDao.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }
}

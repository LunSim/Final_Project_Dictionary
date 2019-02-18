package com.seakleang.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.seakleang.dictionary.adapter.BookmarkAdapter;
import com.seakleang.dictionary.data.dao.DictionaryDao;
import com.seakleang.dictionary.data.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private DictionaryDao dictionaryDao;
    private ListView listView;
    private Toolbar toolbar;
    private List<String> listWord = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        appDatabase = AppDatabase.getFileDatabase(this);
        dictionaryDao = appDatabase.dictionaryDao();
        listView = findViewById(R.id.listViewBookmark);
        toolbar = findViewById(R.id.tbBookmark);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bookmark");

        refreshLisView();

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView textView = view.findViewById(R.id.tvBookmarkWord);

            int wordId = dictionaryDao.getIdByWord(textView.getText().toString());
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("id", wordId);
            intent.putExtra("activity", "BookmarkActivity");
            startActivity(intent);
        });

    }

    private void refreshLisView() {
        listWord = dictionaryDao.getBookmarkWord();
        BookmarkAdapter adapter = new BookmarkAdapter(listWord, dictionaryDao);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.clear_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.menuClear){
            //bookmarkDao.deleteAll();
            for (String word : listWord){
                dictionaryDao.updateBookmarkByWord(word, false);
            }
            refreshLisView();
        }
        if (item.getItemId() == android.R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
